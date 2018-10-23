package com.excilys.cdb.config;

import java.io.File;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@PropertySource("classpath:mysql.properties")
@ComponentScan(basePackages ={"com.excilys.cdb.config", "com.excilys.cdb.mapper", "com.excilys.cdb.persistence",
"com.excilys.cdb.services", "com.excilys.cdb.servlets", "com.excilys.cdb.app", "com.excilys.cdb.validators" })
public class SpringJdbcConfig {

	private final static Logger logger = LoggerFactory.getLogger("SpringJdbcConfig");

	private HikariDataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	
	public SpringJdbcConfig() {
    	File dbPropertiesFile = new File(SpringJdbcConfig.class.getClassLoader().getResource("hikari.properties").getFile());
    	HikariConfig config = new HikariConfig(dbPropertiesFile.getAbsolutePath());
    	dataSource = new HikariDataSource(config);
    	try {
    		Class.forName(dataSource.getDriverClassName());
    	}catch(ClassNotFoundException e){
    		logger.error("Error finding Driver", e);
    	}
    	jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Bean 
	JdbcTemplate jdbcTemplate() {
		return jdbcTemplate;
	}
	
	
}
