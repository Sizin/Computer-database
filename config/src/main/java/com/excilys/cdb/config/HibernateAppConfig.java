package com.excilys.cdb.config;

import java.io.IOException;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.excilys.cdb.core.Company;
import com.excilys.cdb.core.Computer;


@Configuration
@PropertySource("classpath:mysql.properties")
@EnableTransactionManagement
//@ComponentScan(basePackages = { "com.excilys.cdb.config", "com.excilys.cdb.mapper", "com.excilys.cdb.dao",
//		"com.excilys.cdb.services", "com.excilys.cdb.app", "com.excilys.cdb.validators", "com.excilys.cdb.controller" })
@ComponentScan(basePackages = { "com.excilys.cdb.config", "com.excilys.cdb.binding",
		"com.excilys.cdb.service.hiberbateService", "com.excilys.cdb.webapp.controller", "com.excilys.cdb.binding",
		"com.excilys.cdb.webapp.controller", "com.excilys.cdb.webapp." })
public class HibernateAppConfig {

	@Autowired
	private Environment env;

	@Bean
	public HibernateTemplate hibernateTemplate() {
		return new HibernateTemplate(sessionFactory());
	}

	@Bean
	public SessionFactory sessionFactory() {
		LocalSessionFactoryBean lsfb = new LocalSessionFactoryBean();
		lsfb.setDataSource(getDataSource());
		lsfb.setAnnotatedClasses(Computer.class, Company.class);
		lsfb.setHibernateProperties(hibernateProperties());
		try {
			lsfb.afterPropertiesSet();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lsfb.getObject();
	}
	
	@Bean
	public DataSource getDataSource() {
//		BasicDataSource dataSource = new BasicDataSource();
		
		
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getRequiredProperty("db.driver"));
		dataSource.setUrl(env.getRequiredProperty("db.url"));
		dataSource.setUsername(env.getRequiredProperty("db.username"));
		dataSource.setPassword(env.getRequiredProperty("db.password"));
		
		return dataSource;
	}

	@Bean
	public HibernateTransactionManager hibTransMan() {
		return new HibernateTransactionManager(sessionFactory());
	}

	private Properties hibernateProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.dialect", env.getRequiredProperty("hibernate.dialect"));
		properties.put("hibernate.hbm2ddl.auto", env.getRequiredProperty("hibernate.hbm2ddl.auto"));
		properties.put("hibernate.show_sql", env.getRequiredProperty("hibernate.show_sql"));
		return properties;
	}

}