package com.excilys.cdb.model;

import java.time.LocalDate;
import java.util.Optional;

/**
 * @author Sizin
 *
 */
public final class Computer {
	
	private long id;
	private String name;
	private LocalDate introducedDate;
	private LocalDate discontinuedDate;
	private Company company;

	public Computer(long id, String name, LocalDate introducedDate, LocalDate discontinuedDate, Company company) {
		this.id = id;
		this.name = name;
		this.introducedDate = introducedDate;
		this.discontinuedDate = discontinuedDate;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setIntroducedDate(LocalDate introducedDate) {
		this.introducedDate = introducedDate;
	}

	public void setDiscontinuedDate(LocalDate discontinuedDate) {
		this.discontinuedDate = discontinuedDate;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public LocalDate getIntroducedDate() {
		return introducedDate;
	}

	public LocalDate getDiscontinuedDate() {
		return discontinuedDate;
	}
	
	public Company getCompany() {
		return company;
	}
	
	public void setCompany(Company company) {
		this.company = company;
	}
	
//	public Optional<String> getCompanyName() {
//		String companyName = "null";
//		
//		companyName = this.company.getName();
//
//		return Optional.ofNullable(companyName);
//	}
	
	public String getCompanyName() {
		String companyName = "null";
		
		companyName = this.company.getName();

		return companyName;
	}
	
	@Override
	public String toString() {
		String str;
		if (this.company != null) {
			str = " | Computer - Id : "+this.id+"; Name : "+this.name+"; Introduced : "+ this.introducedDate +"; Discontinued : "+ this.discontinuedDate +"; "+ this.company.toString();  
		}else {
			str = " | Computer - Id : "+this.id+"; Name : "+this.name+"; Introduced : "+ this.introducedDate +"; Discontinued : "+ this.discontinuedDate +"; | No Company ";
		}
		
		return str;
	}
		
}



