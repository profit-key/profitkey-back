package com.profitkey.stock.config;

import static java.util.stream.Collectors.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springdoc.core.customizers.OperationCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;

import com.profitkey.stock.annotation.ApiErrorCode;
import com.profitkey.stock.annotation.ApiErrorExceptions;
import com.profitkey.stock.annotation.DisableSwaggerSecurity;
import com.profitkey.stock.annotation.ExplainError;
import com.profitkey.stock.config.environment.config.EnvironmentChecker;
import com.profitkey.stock.dto.common.ErrorReason;
import com.profitkey.stock.dto.common.ErrorResponse;
import com.profitkey.stock.dto.common.ExampleHolder;
import com.profitkey.stock.exception.errorcode.BaseErrorCode;
import com.profitkey.stock.exception.errorcode.ProfitCodeException;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {
	private final ApplicationContext applicationContext;
	private final EnvironmentChecker environmentChecker;

	@Bean
	public OpenAPI swaggerApi() {
		//(기존)
		// return environmentChecker.getSwaggerInfoByEnv();

		//(추가 수정)
		OpenAPI openAPI = environmentChecker.getSwaggerInfoByEnv();  // 기존 설정 가져오기

		// 기존 설정에 보안 스킴 추가
		openAPI.addSecurityItem(new SecurityRequirement().addList("BearerAuth"))
			.components(new Components()
				.addSecuritySchemes("BearerAuth", new SecurityScheme()
					.type(SecurityScheme.Type.HTTP)
					.scheme("bearer")
					.bearerFormat("JWT")
				)
			);
		return openAPI;
	}

	@Bean
	public GroupedOpenApi customOpenApi() {
		// 스웨거 스캔 범위 지정
		return GroupedOpenApi.builder().group("profitkey").packagesToScan("com.profitkey.stock").build();
	}

	@Bean
	public OperationCustomizer customize() {
		return (Operation operation, HandlerMethod handlerMethod) -> {
			DisableSwaggerSecurity methodAnnotation = handlerMethod.getMethodAnnotation(DisableSwaggerSecurity.class);
			ApiErrorExceptions apiErrorExceptionsExample = handlerMethod.getMethodAnnotation(ApiErrorExceptions.class);
			ApiErrorCode apiErrorCodeExample = handlerMethod.getMethodAnnotation(ApiErrorCode.class);

			List<String> tags = getTags(handlerMethod);
			if (methodAnnotation != null) {
				operation.setSecurity(Collections.emptyList());
			}
			if (!tags.isEmpty()) {
				operation.setTags(Collections.singletonList(tags.get(0)));
			}
			if (apiErrorExceptionsExample != null) {
				generateExceptionResponseExample(operation, apiErrorExceptionsExample.value());
			}
			if (apiErrorCodeExample != null) {
				generateErrorCodeResponseExample(operation, apiErrorCodeExample.value());
			}
			return operation;
		};
	}

	private void generateErrorCodeResponseExample(Operation operation, Class<? extends BaseErrorCode> type) {
		ApiResponses responses = operation.getResponses();

		BaseErrorCode[] errorCodes = type.getEnumConstants();

		Map<Integer, List<ExampleHolder>> statusWithExampleHolders = Arrays.stream(errorCodes).map(baseErrorCode -> {
			try {
				ErrorReason errorReason = baseErrorCode.getErrorReason();
				return ExampleHolder.builder()
					.holder(getSwaggerExample(baseErrorCode.getExplainError(), errorReason))
					.code(errorReason.getStatus())
					.name(errorReason.getCode())
					.build();
			} catch (NoSuchFieldException e) {
				throw new RuntimeException(e);
			}
		}).collect(groupingBy(ExampleHolder::getCode));

		addExamplesToResponses(responses, statusWithExampleHolders);
	}

	private void generateExceptionResponseExample(Operation operation, Class<?> type) {
		ApiResponses responses = operation.getResponses();

		Object bean = applicationContext.getBean(type);
		Field[] declaredFields = bean.getClass().getDeclaredFields();
		Map<Integer, List<ExampleHolder>> statusWithExampleHolders = Arrays.stream(declaredFields)
			.filter(field -> field.getAnnotation(ExplainError.class) != null)
			.filter(field -> field.getType() == ProfitCodeException.class)
			.map(field -> {
				try {
					ProfitCodeException exception = (ProfitCodeException)field.get(bean);
					ExplainError annotation = field.getAnnotation(ExplainError.class);
					String value = annotation.value();
					ErrorReason errorReason = exception.getErrorReason();
					return ExampleHolder.builder()
						.holder(getSwaggerExample(value, errorReason))
						.code(errorReason.getStatus())
						.name(field.getName())
						.build();
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e);
				}
			})
			.collect(groupingBy(ExampleHolder::getCode));

		addExamplesToResponses(responses, statusWithExampleHolders);
	}

	private static List<String> getTags(HandlerMethod handlerMethod) {
		List<String> tags = new ArrayList<>();

		Tag[] methodTags = handlerMethod.getMethod().getAnnotationsByType(Tag.class);
		List<String> methodTagStrings = Arrays.stream(methodTags).map(Tag::name).collect(Collectors.toList());

		Tag[] classTags = handlerMethod.getClass().getAnnotationsByType(Tag.class);
		List<String> classTagStrings = Arrays.stream(classTags).map(Tag::name).collect(Collectors.toList());
		tags.addAll(methodTagStrings);
		tags.addAll(classTagStrings);
		return tags;
	}

	private Example getSwaggerExample(String value, ErrorReason errorReason) {
		ErrorResponse errorResponse = new ErrorResponse(errorReason, "요청 경로");
		Example example = new Example();
		example.description(value);
		example.setValue(errorResponse);
		return example;
	}

	private void addExamplesToResponses(ApiResponses responses,
		Map<Integer, List<ExampleHolder>> statusWithExampleHolders) {
		statusWithExampleHolders.forEach((status, v) -> {
			Content content = new Content();
			MediaType mediaType = new MediaType();
			ApiResponse apiResponse = new ApiResponse();
			v.forEach(exampleHolder -> {
				mediaType.addExamples(exampleHolder.getName(), exampleHolder.getHolder());
			});
			content.addMediaType("application/json", mediaType);
			apiResponse.setContent(content);
			responses.addApiResponse(status.toString(), apiResponse);
		});
	}
}
