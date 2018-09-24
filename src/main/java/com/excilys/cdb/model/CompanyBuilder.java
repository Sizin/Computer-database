package com.excilys.cdb.model;

public class CompanyBuilder {
	private long id;
	private String name;
	
	public CompanyBuilder setId(long id) {
		this.id = id;
		return this;
	}
	
	public CompanyBuilder setName(String name) {
		this.name = name;
		return this;
	}
	
	public Company build() {
		return new Company(this.id, this.name);
	}
	
}
