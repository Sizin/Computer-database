package com.excilys.cdb.persistence;

import java.util.List;

import com.excilys.cdb.core.Company;


public interface HibernateCompanyDaoInterface {
	String SELECT_ALL = "FROM Company";
	
	List<Company> findAll();	
}
