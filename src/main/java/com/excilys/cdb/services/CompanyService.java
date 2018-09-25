package com.excilys.cdb.services;

import java.util.List;
import java.util.Optional;

import javax.swing.plaf.synth.SynthSpinnerUI;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.CompanyDao;

/**
 * @author Sizin
 * Computer Service Singleton class
 *
 */
public class CompanyService{

	private static CompanyService companyService = null;
	private static CompanyDao companyDao = null;
	
	
	private CompanyService() {
		this.companyDao 		= CompanyDao.getInstance();
	}
	
	/**
	 * @return An instance of CoputerService
	 */
	public static CompanyService getInstance() {
		if(companyService == null ) {
			companyService = new CompanyService();
		}
		return companyService;
	}

	/**
	 * Diplay all the computers
	 *  
	 * @return a List of computers
	 */
	public static List<Company> showCompanies() {
		List<Company> companies = companyDao.getAllCompanies();
		return companies;
	}
	
	public static Optional<Company> getCompany(long id) {
		Company company = null;
		company = companyDao.getCompanyDetails(id);
		return Optional.ofNullable(company);
	}
	
		
}
