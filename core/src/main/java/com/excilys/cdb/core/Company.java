package com.excilys.cdb.core;

import javax.persistence.*;

/**
 * @author Sizin
 *
 */
@Entity
@Table(name="company")
//@NamedQueries(value = {
//	    @NamedQuery(name = "Company.getAll", query = "SELECT compa FROM Company compa")
//	})
public class Company {
	@Id
	@Column(name = "id")
	private long id;
	@Column(name = "name")
	private String name;	
	
	public Company() {
	}
	
	public Company(long id) {
		this.id = id;
	}
	
	public Company(long id, String name) {
		this.id 	= id;
		this.name 	= name;
	}
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getName() {
		if(this.name == null) {
			return "null";
		}else {
			return this.name;
		}

	}
	
	@Override
	public String toString() {
		return "Company{"+
				"id="+ id +
				", name='" + name +'\'' +
				'}';
	}
	
}
