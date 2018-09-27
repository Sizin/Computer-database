package com.excilys.cdb.model;

import javax.persistence.*;

/**
 * @author Sizin
 *
 */
@Entity
@NamedQueries(value = {
	    @NamedQuery(name = "Company.getAll", query = "SELECT compa FROM Company compa")
	})
public class Company {
	@Id
	private long id;
	private String name;
	
	public Company() {
	}
	
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
	
	@Override
	public String toString() {
		return "Company{"+
				"id="+ id +
				", name='" + name +'\'' +
				'}';
	}
	
}
