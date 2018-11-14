package com.excilys.cdb.service.hibernateService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.core.Company;
import com.excilys.cdb.persistence.hibernatePersistence.HibernateCompanyDao;

@Service
public class HibernateCompanyService {

	@Autowired
	private HibernateCompanyDao hcompanyDao;
	
	@Transactional(readOnly = true)
	public List<Company> getAll(){
		return hcompanyDao.findAll();	
	}
	
	@Transactional(readOnly = true)
	public Company getOne(int id){
		return hcompanyDao.findId(id);	
	}
	

	
}
