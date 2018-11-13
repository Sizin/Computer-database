package com.excilys.cdb.binding;

import java.time.LocalDate;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;

import org.springframework.stereotype.Component;

import com.excilys.cdb.binding.DateFormatException;
import com.excilys.cdb.binding.DateRangeException;

@Component
public class DateValidator {
	
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	public boolean checkDates(String date1, String date2) throws DateFormatException, DateRangeException {
		LocalDate date = null;
		
		LocalDate localDate1 = null;
		LocalDate localDate2 = null;
		
		boolean result = true;
		
		//Verifying date1 (aka: IntrodcuedDate)
		if(validDate(date1)) {
			if(isValidFormat(date1)) {
				localDate1 = LocalDate.parse(date1);
			}else {
				throw new DateFormatException();
			}
		}else {
			throw new DateFormatException();
		}
		
		//Verifying date2 (aka: discontinuedDate);
		if(validDate(date2)) {
			if(isValidFormat(date2)) {
				localDate2 = LocalDate.parse(date2);
				
				// Checks if discontinued date is greater or equal to introduced date
				if (localDate1 != null && isGreaterDate(localDate1, localDate2)) {
					result = true;
				} else if(localDate1 == null && localDate2 != null) {
					throw new DateRangeException();
				}
				
			}else {
				throw new DateFormatException();
			}
		}else {
			throw new DateFormatException();
		}
		

		return result;

	}
	
	
	
	/**
	 * @param format dd/MM/yyyy OR yyyy-MM-dd
	 * @param value The date to check in String format
	 * @return true or false according to the check
	 */
	public boolean isValidFormat(String value) {
	    DateTimeFormatter[] formatters = {
	    		new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd HH:mm:ss.S").toFormatter()
	    };
	    
	    for(DateTimeFormatter formatter: formatters) {
	    	try {
	    		LocalDate.parse(value, formatter);
	    	}catch(DateTimeParseException e) {
	    	}
	    }
	    return true;
	}
	
	public boolean validDate(String dateString) throws DateFormatException, DateRangeException {		
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
	
	public boolean isGreaterDate(LocalDate date1, LocalDate date2) throws DateRangeException {	
		if(date1 == null) {
			throw new DateRangeException();
		}
		if(date2 != null ) {
			if(date1.compareTo(date2) <= 0) { // if introduced date greater or equal to introducedDate
				return true;
			}else {
				throw new DateRangeException();
			}
		}else {
			return true;
		}

	}
	
	public boolean isDateStringEmpty(String dateString) {
		if (dateString != null && dateString != "" ) {
			return false;
		}else {
			return true;
		}
	}
	
	
	
}
