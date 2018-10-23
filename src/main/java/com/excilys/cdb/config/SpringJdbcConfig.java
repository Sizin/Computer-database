package com.excilys.cdb.persistence;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@PropertySource("classpath:mysql.properties")
public class SpringJdbcConfig {

	@Autowired
	Environment environment;
	
	
	private final String DB_URL = "DB_URL";
	private final String DB_USERNAME = "DB_USERNAME";
	private final String DB_DRIVER = "DB_DRIVER";
	private final String DB_PASSWORD = "DB_PASSWORD";
	
	
    @Bean
    public DataSource mysqlDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setUrl(environment.getProperty(DB_URL));
//        dataSource.setUsername(environment.getProperty(DB_USERNAME));
//        dataSource.setPassword(environment.getProperty(DB_PASSWORD));
//        dataSource.setDriverClassName(environment.getProperty(DB_DRIVER));
        
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/computer-database-db?useSSL=false");
        dataSource.setUsername("admincdb");
        dataSource.setPassword("qwerty1234");
 
        return dataSource;
    }
	
}
