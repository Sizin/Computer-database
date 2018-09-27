package com.excilys.cdb.model;

import java.time.LocalDate;
import java.util.Optional;

import javax.persistence.*;

/**
 * @author Sizin
 *
 */
@Entity
@NamedQueries(value = {
	    @NamedQuery(name = "Computer.getAll", query = "SELECT compu FROM Computer compu")
	})
public final class Computer {
	
	private long id;
	private String name;
	private LocalDate introducedDate;
	private LocalDate discontinuedDate;
	private Company company = new Company();

	public Computer() {
		
	}
	
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
	
	public String getCompanyName() {
		return this.company.getName();
	}
	
	@Override
	public String toString() {
		return "Computer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", introducedDate='" + introducedDate + '\'' +
                ", discontinudedDate='" + discontinuedDate + '\'' +
                ", " + company.toString() +
                "}\n";
	}
	
}



