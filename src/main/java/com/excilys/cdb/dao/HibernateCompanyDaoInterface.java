package com.excilys.cdb.dao;

import java.util.List;

import com.excilys.cdb.model.Company;


public interface HibernateCompanyDaoInterface {
	String SELECT_ALL = "FROM Company";
	
	List<Company> findAll();	
}
