package com.excilys.cdb.binding;

public class ComputerNameException extends Exception{

	public ComputerNameException() {
		System.out.println("The computer name should be at least 2 characters");
	}
	
}
