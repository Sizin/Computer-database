package com.excilys.cdb.validators;

import java.time.LocalDate;

import com.excilys.cdb.exceptions.ComputerNameException;
import com.excilys.cdb.exceptions.DateFormatException;
import com.excilys.cdb.exceptions.DateRangeException;

import com.excilys.cdb.validators.DateValidator;

public class ComputerValidator {

	public ComputerValidator() {

	}

	public static boolean validateName(String computerName) throws ComputerNameException {
		String[] arr = computerName.split("[~#@*+%{}<>\\[\\]|\"\\_^]", 2);

		// if arr.length > 1 name contain one of the character in the patern
		if (arr.length > 1) {
			throw new ComputerNameException();
		} else if (computerName.length() < 2) {
			throw new ComputerNameException();
		}
		return true;
	}

	public static boolean validateDates(String date1, String date2) throws DateFormatException, DateRangeException {
		LocalDate date = null;
		
		LocalDate localDate1 = null;
		LocalDate localDate2 = null;
		
		boolean result = true;
		
		//Verifying date1 (aka: IntrodcuedDate)
		if(DateValidator.validDate(date1)) {
			if(DateValidator.isValidFormat(date1)) {
				System.out.println(date1);
				localDate1 = LocalDate.parse(date1);
			}else {
				throw new DateFormatException();
			}
			
			//Verifying date2 (aka: discontinuedDate); 
			//Should be verified only if firstdate is valid
			if(DateValidator.validDate(date2)) {
				if(DateValidator.isValidFormat(date2)) {
					localDate2 = LocalDate.parse(date2);
				}else {
					throw new DateFormatException();
				}
			}else {
				return false;
			}
			
			// Checks if discontinued date is greater or equal to introduced date
			if (localDate1 != null && DateValidator.isGreaterDate(localDate1, localDate2)) {
				result = true;
			} else if(localDate1 == null && localDate2 != null) {
				throw new DateRangeException();
			}
		}else {
			result = false;
		}
		
		return result;

	}


}
