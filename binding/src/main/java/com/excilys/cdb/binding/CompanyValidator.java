package com.excilys.cdb.binding;

import java.text.ParseException;

import com.excilys.cdb.binding.CompanyNameException;

public class CompanyValidator {

	public CompanyValidator() {
		
	}
	
	public static boolean validateId(String idString, int nbCompany) throws ParseException {
		if(idString == null) {
			throw new ParseException("Company ID should be superior to zero", 0);
		}else {
			long id = Integer.parseInt(idString);
			if( id < 0) {
				throw new ParseException("Company ID should be superior to zero", 0);
			}else if( id > nbCompany - 1) {
				throw new ParseException("Company ID not found", 0);
			}
		}
		return true;
	}
	
	public static boolean validateName(String nameCompany) throws CompanyNameException {
	    String[] arr = nameCompany.split("[~#@*+%{}<>\\[\\]|\"\\_^]", 2);
	    
	    // if arr.length > 1 name contain one of the character in the patern
	    if(arr.length > 1) {
	    	throw new CompanyNameException();
	    }
	    
		return true;
	}
	
	
	
	
}