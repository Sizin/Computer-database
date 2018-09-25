package com.excilys.cdb.exceptions;

public class DateFormatException extends Exception {

	public DateFormatException() {
		System.out.println("The date format should be (YYYY-MM-DD). Try again.");
	}

}
