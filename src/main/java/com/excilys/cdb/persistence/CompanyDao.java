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

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.CompanyBuilder;


public class CompanyDao {

	private static String DB_URL;
	private static String DB_USERNAME;
	private static String DB_PASSWORD;
	
	private static CompanyDao companyDao = null;
	private Connection con;
	
	private static final String GET_ALL = "SELECT id, name FROM company;";
	private static final String GET_ONE = "SELECT id, name FROM company WHERE id=?;";
	private static final String GET_COUNT = "SELECT COUNT(id) as count FROM company";
	
	private CompanyDao() {
		try {			
			Properties props = new Properties();
			InputStream inputStream = getClass().getResourceAsStream("/mysql.properties");
			props.load(inputStream);
	
			DB_URL = props.getProperty("DB_URL");
			DB_USERNAME = props.getProperty("DB_USERNAME");
			DB_PASSWORD = props.getProperty("DB_PASSWORD");
		}catch(IOException e) {
			System.out.println(e);
		}
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static CompanyDao getInstance() {
		if(companyDao == null) {
			companyDao = new CompanyDao();
		}
		return companyDao;
	}
	
	public int getCompanyCount() {
		int rows = 0;
		try (Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)){
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
		try (Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)){
			PreparedStatement getComputerPstmt 	= con.prepareStatement(GET_ONE);
			getComputerPstmt.setLong(1, company.getId());
			ResultSet companyRs	= getComputerPstmt.executeQuery();
			if(companyRs.next()) {
				companyBuilder.setId(companyRs.getInt("id"));
				companyBuilder.setName(companyRs.getString("name"));
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
		try (Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)){
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
	
	
	
	
}