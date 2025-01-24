package com.profitkey.stock.util;

import com.profitkey.stock.dto.KisApiProperties;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class HeaderUtil {
	private static KisApiProperties kisApiProperties;

	public HeaderUtil(KisApiProperties kisApiProperties) {
		HeaderUtil.kisApiProperties = kisApiProperties;
	}

	public static Map<String, String> getCommonHeaders() {
		Map<String, String> headers = new HashMap<>();

		headers.put("Content-Type", "application/json");
		headers.put("authorization", "Bearer "
			+ "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0b2tlbiIsImF1ZCI6IjAwYzE5ZmU0LWJkZGYtNGYxYy05ZDdkLWY4ZjFlYmU4ZGRmNCIsInByZHRfY2QiOiIiLCJpc3MiOiJ1bm9ndyIsImV4cCI6MTczNzc4NzcyOSwiaWF0IjoxNzM3NzAxMzI5LCJqdGkiOiJQU3hOZUJuY25EbE1GOGlnVDh3MzlaeVBkakFJb2k2ZGhDWjYifQ.buf6lmFQcW5jIy1EvzXiuvzTXzU8690Y02I5VkyGbQm1FciN6ufkFkj5fvSPhZ5F-DGZbDTbPQhVrf_HSbu1Vg");
		headers.put("appKey", kisApiProperties.getApiKey());
		headers.put("appSecret", kisApiProperties.getSecretKey());
		return headers;
	}

	public static Map<String, String> getHeadersFromDto(Object dto) {
		Map<String, String> headers = new HashMap<>();
		Field[] fields = dto.getClass().getDeclaredFields();

		for (Field field : fields) {
			try {
				field.setAccessible(true);
				Object value = field.get(dto);
				headers.put(field.getName(), value == null ? "" : value.toString());
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return headers;
	}

}
