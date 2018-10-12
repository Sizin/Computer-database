package com.excilys.cdb.persistence;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.CompanyBuilder;


public class CompanyDao {
	
	private final ConnectionManager connection = ConnectionManager.CONNECTION;
	
	private final Logger logger = LoggerFactory.getLogger("CompanyDao");
	
	private static CompanyDao companyDao = null;
	private static ComputerDao computerDao = null;
	
	private Connection con;
	
	private static final String GET_ALL = "SELECT id, name FROM company;";
	private static final String GET_ONE = "SELECT id, name FROM company WHERE id=?;";
	private static final String GET_COUNT = "SELECT COUNT(id) as count FROM company";
	private static final String DELETE = "DELETE FROM company WHERE id = ?";
	
	
	
	public static CompanyDao getInstance() {
		if(companyDao == null) {
			companyDao = new CompanyDao();
		}
		return companyDao;
	}
	
	public int getCompanyCount() {
		int rows = 0;
		try (Connection con = connection.getConnection()){
			Statement getCountStmt = con.createStatement();
			ResultSet getCountRs = getCountStmt.executeQuery(GET_COUNT);
			if(getCountRs.next()) {
				rows = getCountRs.getInt("count");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}  
		return rows;
	}
	
	public Company get(Company company){

		CompanyBuilder companyBuilder = new CompanyBuilder();
		try (Connection con = connection.getConnection()){
			PreparedStatement getComputerPstmt 	= con.prepareStatement(GET_ONE);
			getComputerPstmt.setLong(1, company.getId());
			ResultSet companyRs	= getComputerPstmt.executeQuery();
			if(companyRs.next()) {
				companyBuilder.setId(companyRs.getInt("id")).setName(companyRs.getString("name"));
				company = companyBuilder.build();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}  
		
		return company;
	}
	
	/**
	 * Retrieves all the companies from the compan1y table
	 * 
	 * @return List of companies
	 */
	public List<Company> getAllCompanies(){
		List<Company> companies = new ArrayList<Company>();
		try (Connection con = connection.getConnection()){
			Statement stmt 	= con.createStatement();
			ResultSet rs	= stmt.executeQuery(GET_ALL);
			while(rs.next()) {
				companies.add(new Company(rs.getInt("id"), rs.getString("name")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}  
		return companies;
	}
	
	
	
	public void deleteCompany(Company company) {
		Connection con = null;
		try{
			con = connection.getConnection();
			con.setAutoCommit(false);
			
			computerDao.deleteComputerUsingCompany(company.getId(), con);
			
			PreparedStatement deleteCompanyPStmt = con.prepareStatement(DELETE);
			deleteCompanyPStmt.setLong(1, company.getId());
			deleteCompanyPStmt.executeUpdate();
			con.commit();
			con.setAutoCommit(true);
			
		}catch(SQLException e) {
			try {
				con.rollback();
			}catch(SQLException e1){
				logger.error("Connection closing failed");
			}
		}finally {
			try {
				con.close();
			}catch(SQLException e) {
				logger.error("Connection closing failed");
			}
		}
		
		
		
		
		
		
		
	}
	
	
	
	
	
}