package com.profitkey.stock.util;

import java.math.BigDecimal;

public class DataConversionUtil {

	public static BigDecimal toBigDecimal(Object value) {
		return value != null ? new BigDecimal(value.toString()) : BigDecimal.ZERO;
	}

	public static Integer toInteger(Object value) {
		return value != null ? Integer.parseInt(value.toString()) : 0;
	}

	public static Long toLong(Object value) {
		return value != null ? Long.parseLong(value.toString()) : 0L;
	}
}

