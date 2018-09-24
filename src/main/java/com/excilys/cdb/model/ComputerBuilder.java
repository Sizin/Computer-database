package com.excilys.cdb.model;

import java.time.LocalDate;

public class ComputerBuilder{
	private long id;
	private String name;
	private LocalDate introducedDate;
	private LocalDate discontinuedDate;
	private Company company;
	
	
	private long companyId;
	
	public ComputerBuilder setId(long id) {
		this.id = id;
		return this;
	}
	
	public ComputerBuilder setName(final String name) {
		this.name = name;
		return this;
	}
	
	public ComputerBuilder setIntroducedDate(LocalDate introducedDate) {
		this.introducedDate = introducedDate;
		return this;		
	}
	
	public ComputerBuilder setDiscontinuedDate(LocalDate discontinuedDate) {
		this.discontinuedDate = discontinuedDate;
		return this;
	}
	
	public ComputerBuilder setCompany(Company company) {
		this.company = company;
		
		return this;
	}
	
	public Computer build() {
		return new Computer(id, name, introducedDate, discontinuedDate, company);
	}
}