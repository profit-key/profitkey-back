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
			+ "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0b2tlbiIsImF1ZCI6ImJmOWI1OTVhLWU1ZmQtNDU4MC04NzA4LWI3NDE0NjhmZWUyNyIsInByZHRfY2QiOiIiLCJpc3MiOiJ1bm9ndyIsImV4cCI6MTczNzkzOTMyNSwiaWF0IjoxNzM3ODUyOTI1LCJqdGkiOiJQU3hOZUJuY25EbE1GOGlnVDh3MzlaeVBkakFJb2k2ZGhDWjYifQ.lnIb1fdUbMQj_Fjx4-XalTptDQS1HqdLNOJ5eBNfPFa3D8tMG6m_WLmN1cPAjfq_WmB7IZLPTa5__MnxXHvdHA");
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
