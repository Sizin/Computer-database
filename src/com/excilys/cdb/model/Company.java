package com.excilys.cdb.model;

/**
 * @author Sizin
 *
 */
public class Company {
	private long id;
	private String name;
	
	
	public Company(long id, String name) {
		this.id 	= id;
		this.name 	= name;
	}
	
	
	public String toString() {
		String str;
		str = " | Company - Id : "+this.id+" Name : "+this.name; 
		return str;
	}
}
