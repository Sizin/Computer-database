package com.excilys.cdb.model;

import java.time.LocalDate;

import javax.persistence.*;

/**
 * @author Sizin
 *
 */
@Entity
@Table(name="Computer")
@NamedQueries(value = {
	    @NamedQuery(name = "Computer.getAll", query = "SELECT compu FROM Computer compu")
	})
public final class Computer {
	@Id
	private long id;
	private String name;
	private LocalDate introduced;
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

	public void setIntroduced(LocalDate introducedDate) {
		this.introduced = introducedDate;
	}

	public void setDiscontinued(LocalDate discontinuedDate) {
		this.discontinued = discontinuedDate;
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
	
	public long getCompanyId() {
		return this.company.getId();
	}
	
	@Override
	public String toString() {
		return "Computer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", introducedDate='" + introduced + '\'' +
                ", discontinudedDate='" + discontinued + '\'' +
                ", " + company.toString() +
                "}\n";
	}
	
}



