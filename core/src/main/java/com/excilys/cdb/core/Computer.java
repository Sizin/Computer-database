package com.excilys.cdb.core;

import java.time.LocalDate;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author Sizin
 *
 */
@Entity
@Table(name="computer")
//@NamedQueries(value = {
//	    @NamedQuery(name = "Computer.getAll", query = "SELECT compu FROM Computer compu")
//	})
public final class Computer {
	@Id
	@Column(name = "id")
	private long id;
	
	@Column(name = "name")
	private String name;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "introduced")
	private LocalDate introduced;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "discontinued")
	private LocalDate discontinued;
	

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="company_id", foreignKey = @ForeignKey(name = "fk_computer_company_1"))
	private Company company = new Company();

	public Computer() {
		
	}
	
	public Computer(long id, String name, LocalDate introducedDate, LocalDate discontinuedDate, Company company) {
		this.id = id;
		this.name = name;
		this.introduced = introducedDate;
		this.discontinued = discontinuedDate;
		this.company = company;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setIntroduced(LocalDate introduced) {
		this.introduced = introduced;
	}

	public void setDiscontinued(LocalDate discontinued) {
		this.discontinued = discontinued;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public LocalDate getIntroduced() {
		return introduced;
	}

	public LocalDate getDiscontinued() {
		return discontinued;
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
		if (this.company == null) {
			return "Computer{" +
	                "id=" + id +
	                ", name='" + name + '\'' +
	                ", introduced='" + introduced + '\'' +
	                ", discontinuded='" + discontinued + '\'' +
	                "}\n";
		}else {
			return "Computer{" +
	                "id=" + id +
	                ", name='" + name + '\'' +
	                ", introduced='" + introduced + '\'' +
	                ", discontinuded='" + discontinued + '\'' +
	                ", " + company.toString() +
	                "}\n";
		}
	}
	
}



