package com.excilys.cdb.verifyers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.Locale;

public class LocalDateCheck {
	
	/**
	 * @param format dd/MM/yyyy OR yyyy-MM-dd
	 * @param value The date to check in String format
	 * @return true or false according to the check
	 */
	public static boolean isValidFormat(String value) {
	    DateTimeFormatter[] formatters = {
	    		new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd").parseDefaulting(ChronoField.HOUR_OF_DAY, 2).parseDefaulting(ChronoField.MINUTE_OF_HOUR, 2).parseDefaulting(ChronoField.SECOND_OF_MINUTE,2).toFormatter()
	    		,new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd HH").parseDefaulting(ChronoField.MINUTE_OF_HOUR, 2).parseDefaulting(ChronoField.SECOND_OF_MINUTE,2).toFormatter()
	    		,new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd HH:mm").parseDefaulting(ChronoField.SECOND_OF_MINUTE,2).toFormatter()
	    		,new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd HH:mm:ss").toFormatter()
	    };
	    
	    for(DateTimeFormatter formatter: formatters) {
	    	try {
	    		LocalDate.parse(value, formatter);
	    		return true;
	    	}catch(DateTimeParseException e) {
	    	}
	    }
	    return false;
	}
}
