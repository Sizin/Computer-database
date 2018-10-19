package com.excilys.cdb.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.CompanyDao;

/**
 * @author Sizin
 * Computer Service Singleton class
 *
 */
@Service
public class CompanyService{

	@Autowired
	private CompanyDao companyDao;

	public int getComputerCount() {
		int count = companyDao.getCompanyCount();
		return count;
	}
	
	/**
	 * Diplay all the computers
	 *  
	 * @return a List of computers
	 */
	public List<Company> showCompanies() {
		List<Company> companies = companyDao.getAllCompanies();
		return companies;
	}
	
	public Company getCompany(Company company) {
		return companyDao.get(company);
	}
	
//	public static void deleteCompany(Company company) {
	public void deleteCompany(Company company) {
		companyDao.deleteCompany(company);
	}
		
}
