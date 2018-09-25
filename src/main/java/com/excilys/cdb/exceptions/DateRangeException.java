package com.excilys.cdb.exceptions;

public class DateRangeException extends Exception{

	public DateRangeException() {
		System.out.println("Discontinued date should be at least equal to introduced date");
	}
	
	
}
