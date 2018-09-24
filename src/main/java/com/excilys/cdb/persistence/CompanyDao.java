package com.excilys.cdb.persistence;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.excilys.cdb.model.Company;

public class CompanyDao {

	private static CompanyDao companyDao = null;
	private Connection con;
	
	private static final String GET_ALL = "SELECT id, name FROM company;";
	private static final String GET_ONE = "SELECT id, name FROM company WHERE id=?;";
	
	private CompanyDao() {
		try {			

			Properties props = new Properties();

			InputStream inputStream = getClass().getResourceAsStream("/mysql.properties");
			props.load(inputStream);
			this.con = DriverManager.getConnection(props.getProperty("DB_URL"), props.getProperty("DB_USERNAME"), props.getProperty("DB_PASSWORD") );
		}catch(SQLException | IOException e) {
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
	
	/**
	 * Retrieves all the companies from the compan1y table
	 * 
	 * @return List of companies
	 */
	public List<Company> getAllCompanies(){
		List<Company> companies = new ArrayList<Company>();
		try {
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