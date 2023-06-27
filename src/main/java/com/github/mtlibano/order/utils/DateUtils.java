package com.github.mtlibano.order.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {
	
	private static DateTimeFormatter dtfBr = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	
	public static ZonedDateTime strToZonedDateTime(String dateStr) {
		return LocalDate.parse(dateStr, dtfBr).atStartOfDay(ZoneId.systemDefault());
	}
	
	public static String zonedDateTimeToStr(ZonedDateTime date) {
		return dtfBr.format(date);
	}

}