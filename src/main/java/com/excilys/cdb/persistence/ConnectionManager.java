package com.excilys.cdb.persistence;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;


public enum ConnectionManager{
	
	CONNECTION;
	
	private HikariConfig config = new HikariConfig();
	private DataSource datasource;
	
	ConnectionManager(){
		
		Properties props = new Properties();

		InputStream inputStream = getClass().getResourceAsStream("/hikari.properties");
		try {
			props.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		props.getProperty("driverClassName");
		props.getProperty("jdbcUrl");
		props.getProperty("dataSource.user");
		props.getProperty("dataSource.password");
		props.getProperty("dataSource.serverName");
		props.getProperty("dataSource.portNumber");
		props.getProperty("dataSource.databaseName");
		
		config = new HikariConfig(props);
		datasource = new HikariDataSource(config);
	}
	
	public Connection getConnection() throws SQLException{
		return datasource.getConnection();
	}
}