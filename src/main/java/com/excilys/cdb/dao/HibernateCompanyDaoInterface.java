package com.excilys.cdb.dao;

import java.util.List;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

public interface HibernateCompanyDaoInterface {
	Computer findById(long id);
	List<Company> findAll();	
}
