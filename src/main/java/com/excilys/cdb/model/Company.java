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
	
	public long getId() {
		return this.id;
	}
	
	public String getName() {
		if(this.name == null) {
			return "null";
		}else {
			return this.name;
		}

	}
	
	public String toString() {
		String str;
		str = " | Company - Id : "+this.id+" Name : "+this.name; 
		return str;
	}
}
