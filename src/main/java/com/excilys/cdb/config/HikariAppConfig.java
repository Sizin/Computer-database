package com.excilys.cdb.config;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class HikariAppConfig {

	private HikariDataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	
	private final static Logger logger = LoggerFactory.getLogger("HikariAppConfig");

	
	public HikariAppConfig() {
		File dbPropertiesFile = new File(HikariAppConfig.class.getClassLoader().getResource("hikari.properties").getFile());
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
	JdbcTemplate jdbcTemplate() {
		return jdbcTemplate;
	}
	
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver bean = new InternalResourceViewResolver();
		bean.setPrefix("/WEB-INF/views/");
		bean.setSuffix(".jsp");
		return bean;
	}
	
	
	
}
