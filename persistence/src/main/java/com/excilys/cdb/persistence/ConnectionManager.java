package com.excilys.cdb.persistence;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Repository
public class ConnectionManager {

	private HikariConfig config = new HikariConfig();
	private DataSource datasource;
	private boolean runOnTestDb;
	private final Logger logger = LoggerFactory.getLogger("ConnectionManager");

	public ConnectionManager() {

		Properties props = new Properties();
		InputStream inputStream = null;

		inputStream = getClass().getResourceAsStream("/hikari.properties");

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
		runOnTestDb = false;
	}

	public Connection getConnection() throws SQLException {
		if (runOnTestDb) {
			return DriverManager.getConnection("jdbc:h2:mem:cbd_test;INIT=RUNSCRIPT FROM '~/eclipse-workspace/Cdb/src/test/resources/scripts/test-db.sql'",
					"ADMINCDBTEST", "qwerty1234");
		} else {
			return datasource.getConnection();
		}

	}
}