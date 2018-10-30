package com.excilys.cdb.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

import static org.hibernate.cfg.Environment.*;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = { "com.excilys.cdb.config", "com.excilys.cdb.mapper", "com.excilys.cdb.persistence",
		"com.excilys.cdb.services", "com.excilys.cdb.servlets", "com.excilys.cdb.app", "com.excilys.cdb.validators",
		"com.excilys.cdb.controller" })
public class HibernateAppConfig {
	@Autowired
	private Environment env;

	@Bean
	public LocalSessionFactoryBean getSessionFactory() {
		LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();

		Properties props = new Properties();

		// Setting JDBC properties
		props.put(DRIVER, env.getProperty("DB_DRIVER"));
		props.put(URL, env.getProperty("DB_URL"));
		props.put(USER, env.getProperty("DB_USERNAME"));
		props.put(PASS, env.getProperty("DB_PASSWORD"));

		// Setting Hibernate properties
		props.put(SHOW_SQL, env.getProperty("hibernate.show_sql"));
		props.put(HBM2DDL_AUTO, env.getProperty("hibernate.hbm2ddl.auto"));

		factoryBean.setHibernateProperties(props);
		factoryBean.setAnnotatedClasses(Computer.class, Company.class);

		return factoryBean;
	}

}
