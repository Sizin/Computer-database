package com.excilys.cdb.verifyers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.Locale;
import java.util.Optional;

import com.excilys.cdb.exceptions.DateFormatException;
import com.excilys.cdb.exceptions.DateRangeException;

public class LocalDateCheck {
	
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	
	
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
	
	public static boolean validDate(String dateString) throws DateFormatException, DateRangeException {
		boolean valid = false;
		
		LocalDate date = null;
		
		if(!isDateStringEmpty(dateString)) {
			if(isValidFormat(dateString)) {
				return true;
			}else {
				throw new DateFormatException();
			}
		}else {
			return false;
		}
	}
	
	public static boolean isGreaterDate(LocalDate date1, LocalDate date2) {	
		if(date1.compareTo(date2) <= 0) { // if introduced date greater or equal to introducedDate
			return true;
		}else {
			return false;
		}
	}
	
	public static boolean isDateStringEmpty(String dateString) {
		if (dateString != null && dateString != "" ) {
			return false;
		}else {
			return true;
		}
	}
	
	
	
}
