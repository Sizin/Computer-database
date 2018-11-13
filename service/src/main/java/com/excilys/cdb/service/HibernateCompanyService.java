package com.excilys.cdb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.persistence.HibernateCompanyDao;
import com.excilys.cdb.core.Company;

@Service
public class HibernateCompanyService {

	@Autowired
	private HibernateCompanyDao hcompanyDao;
	
	@Transactional(readOnly = true)
	public List<Company> getAll(){
		return hcompanyDao.findAll();	
	}

	
}
