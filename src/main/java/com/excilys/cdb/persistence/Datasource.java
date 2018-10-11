package com.excilys.cdb.persistence;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class Datasource {

	
	
	private HikariConfig config = new HikariConfig();
	private HikariDataSource ds;
	private static Datasource datasource;
	
	private Datasource() {
		File fileHikariProperties = new File(getClass().getClassLoader().getResource("hikarimysql.properties").getFile());
		config = new HikariConfig(fileHikariProperties.getAbsolutePath());
		ds = new HikariDataSource(config);
	}
	
	public static Datasource getInstance() {
		if(datasource == null) {
			datasource = new Datasource();
		}
		
		return datasource;
		
	}
	
	
	public Connection getConnection() throws SQLException {
		return ds.getConnection();
	}
	
	
	
	
}
