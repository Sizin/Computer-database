package com.excilys.cdb.persistence;


import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

//import com.excilys.cdb.config.SpringJdbcConfig;
import com.excilys.cdb.core.Company;
import com.excilys.cdb.core.CompanyBuilder;
import com.excilys.cdb.core.Computer;

@Repository
public class CompanyDao {

	@Autowired
	private ConnectionManager connection;
	
	@Autowired
	private ComputerDao computerDao;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	private CompanyRowMapper companyRowMapper;
	
	private final Logger logger = LoggerFactory.getLogger("CompanyDao");
	
	private static final String GET_ALL = "SELECT id, name FROM company;";
	private static final String GET_ONE = "SELECT id, name FROM company WHERE id=?;";
	private static final String GET_COUNT = "SELECT COUNT(id) as count FROM company";
	private static final String DELETE = "DELETE FROM company WHERE id = ?";
	private static final String DELETE_COMPUTER_CORRESPONDING_TO_COMPANY = "DELETE FROM computer WHERE company_id = ? ";
	
	/**
	 * Get the number of companies in the 'company' table
	 * 
	 * @return int the number of companies
	 */
	public int getCompanyCount() {
		return jdbcTemplate.queryForObject(GET_COUNT, Integer.class);
	}
	
	/**
	 * Get one company
	 * 
	 * @param company object that should hold an Id
	 * @return the corresponding company object
	 */
	public Company get(Company company){
		return (Company)jdbcTemplate.queryForObject(GET_ONE, new Object[] {company.getId()} , companyRowMapper);
	}
	
	/**
	 * Retrieves all the companies from the company table
	 * 
	 * @return List of companies
	 */
	public List<Company> getAllCompanies(){
		return jdbcTemplate.query(GET_ALL, companyRowMapper);
	}
		
	public void deleteCompany(Company company) {
		jdbcTemplate.update(DELETE_COMPUTER_CORRESPONDING_TO_COMPANY, company.getId());
		jdbcTemplate.update(DELETE, company.getId());
	}
	
}