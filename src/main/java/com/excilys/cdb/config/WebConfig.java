package com.excilys.cdb.config;

import java.io.File;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
@EnableWebMvc
@PropertySource("classpath:mysql.properties")
@ComponentScan(basePackages = { "com.excilys.cdb.config", "com.excilys.cdb.mapper", "com.excilys.cdb.persistence",
		"com.excilys.cdb.services", "com.excilys.cdb.servlets", "com.excilys.cdb.app", "com.excilys.cdb.validators",
		"com.excilys.cdb.controller" })
public class WebConfig implements WebMvcConfigurer {

	private HikariDataSource dataSource;
	private JdbcTemplate jdbcTemplate;

	private final static Logger logger = LoggerFactory.getLogger("WebConfig");

	public WebConfig() {
		File dbPropertiesFile = new File(WebConfig.class.getClassLoader().getResource("hikari.properties").getFile());
		HikariConfig config = new HikariConfig(dbPropertiesFile.getAbsolutePath());
		dataSource = new HikariDataSource(config);
		try {
			Class.forName(dataSource.getDriverClassName());
		} catch (ClassNotFoundException e) {
			logger.error("Error finding Driver", e);
		}
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver bean = new InternalResourceViewResolver();
		bean.setPrefix("/WEB-INF/views/");
		bean.setSuffix(".jsp");
		return bean;
	}

	@Bean
	JdbcTemplate jdbcTemplate() {
		return jdbcTemplate;
	}

}