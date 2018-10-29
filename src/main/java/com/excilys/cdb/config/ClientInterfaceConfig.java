package com.excilys.cdb.config;

import java.io.File;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan(basePackages = { "com.excilys.cdb.app", "com.excilys.cdb.mapper", "com.excilys.cdb.persistence", "com.excilys.cdb.services", "com.excilys.cdb.validators"})
public class ClientInterfaceConfig {

	private HikariDataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	private final static Logger logger = LoggerFactory.getLogger("ClientInterfaceConfig");

	public ClientInterfaceConfig() {

		File dbPropertiesFile = new File(ClientInterfaceConfig.class.getClassLoader().getResource("hikari.properties").getFile());
		HikariConfig config = new HikariConfig(dbPropertiesFile.getAbsolutePath());
		dataSource = new HikariDataSource(config);
		try {
			Class.forName(dataSource.getDriverClassName());
		} catch (ClassNotFoundException e) {
			logger.error("Error finding Driver", e);
		}
		jdbcTemplate = new JdbcTemplate(dataSource);


	}
	
	@Bean
	public JdbcTemplate jdbcTemplate() {
		return jdbcTemplate;
	}
	

}
