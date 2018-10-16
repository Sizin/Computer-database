package com.excilys.cdb.persistence;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public enum ConnectionTestManager{
	CONNECTION;

	
	private HikariConfig config = new HikariConfig();
	private DataSource datasource;
	
	ConnectionTestManager(){
		
		Properties props = new Properties();
		InputStream inputStream = null;
		
		inputStream = getClass().getResourceAsStream("/hikaritest.properties");
		
		try {
			props.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		props.getProperty("driverClassName");
		props.getProperty("jdbcUrl");
		props.getProperty("dataSource.user");
		props.getProperty("dataSource.password");

		config = new HikariConfig(props);
		datasource = new HikariDataSource(config);
	}
	
	public Connection getConnection() throws SQLException{
		return datasource.getConnection();
	}
}