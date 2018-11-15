package com.excilys.cdb.webapp.security;

import java.io.IOException;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import com.excilys.cdb.core.Company;
import com.excilys.cdb.core.Computer;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = { "com.excilys.cdb.config", "com.excilys.cdb.persistence.hibernatePersistence",
		"com.excilys.cdb.service.hibernateService", "com.excilys.cdb.binding",
		"com.excilys.cdb.service.hibernateService", "com.excilys.cdb.webapp.controller" })
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    @Autowired
//    PasswordEncoder passwordEncoder;

//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
////		auth.inMemoryAuthentication().withUser("user").password(passwordEncoder.encode("password")).roles("USER");
////		auth.inMemoryAuthentication().withUser("admin").password(passwordEncoder.encode("password")).roles("ADMIN");
////		auth.inMemoryAuthentication().withUser("visitor").password(passwordEncoder.encode("password")).roles("VISITOR");
////		
//		auth.inMemoryAuthentication().withUser("name").password("password").roles("USER");
//
////		auth.jdbcAuthentication().dataSource(getDataSource())
////				.usersByUsernameQuery("select name,password from users where name=?")
////				.authoritiesByUsernameQuery("select name, role from role where name=?");
////				.passwordEncoder(passwordEncoder);
//
//	}

//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
////		http.authorizeRequests()
////		.antMatchers("/", "/Dashboard").permitAll()
////		.antMatchers("/addComputer", "/editComputer").hasAnyRole("ADMIN", "USER")
////		.and().formLogin().loginPage("/login").defaultSuccessUrl("/Dashboard")
////		.and().logout().logoutUrl("/logout").logoutSuccessUrl("/Dashboard");
////		http.formLogin().loginPage("/login");
//
//        http.authorizeRequests()
//        .antMatchers("/login")
//            .permitAll()
//        .antMatchers("/**")
//            .hasAnyRole("ADMIN", "USER", "VISITOR")
//        .and()
//            .formLogin()
//            .loginPage("/login")
//            .defaultSuccessUrl("/Dashboard")
//            .failureUrl("/login?error=true")
//            .permitAll()
//        .and()
//            .logout()
//            .logoutSuccessUrl("/login?logout=true")
//            .invalidateHttpSession(true)
//            .permitAll()
//        .and()
//            .csrf()
//			.disable();
//		
////        http.authorizeRequests()
////        .antMatchers("/login")
////            .permitAll()
////        .antMatchers("/**")
////            .hasAnyRole("ADMIN", "USER", "VISITOR")
////        .and()
////            .formLogin()
////            .loginPage("/login")
////            .defaultSuccessUrl("/Dashboard")
////            .failureUrl("/login?error=true")
////            .permitAll()
////        .and()
////            .logout()
////            .logoutSuccessUrl("/login?logout=true")
////            .invalidateHttpSession(true)
////            .permitAll()
////        .and()
////            .csrf()
////			.disable();
//		
////		http.authorizeRequests().anyRequest().hasAnyRole("ADMIN", "USER", "VISITOR").and().authorizeRequests()
////				.antMatchers("/login**").permitAll().and().formLogin().loginPage("/login")
////				.loginProcessingUrl("/loginAction").permitAll().and().formLogin().defaultSuccessUrl("/Dashboard/", true)
////				.and().logout().logoutSuccessUrl("/login").permitAll().and().csrf().disable();
//
//	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {

		// in-memory authentication
		// auth.inMemoryAuthentication().withUser("pankaj").password("pankaj123").roles("USER");

		// using custom UserDetailsService DAO
		// auth.userDetailsService(new AppUserDetailsServiceDAO());


		final String findUserQuery = "select username,password,enabled " + "from user " + "where username = ?";
		final String findRoles = "select username,role " + "from role " + "where username = ?";

		auth.jdbcAuthentication().dataSource(getDataSource()).usersByUsernameQuery(findUserQuery)
				.authoritiesByUsernameQuery(findRoles);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
				// Spring Security should completely ignore URLs ending with .html
				.antMatchers("/*.html");
	}

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
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/computer-database-db?useSSL=false");
		dataSource.setUsername("admincdb");
		dataSource.setPassword("qwerty1234");

		return dataSource;
	}

	@Bean
	public HibernateTransactionManager hibTransMan() {
		return new HibernateTransactionManager(sessionFactory());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	private Properties hibernateProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		properties.put("hibernate.hbm2ddl.auto", "update");
		properties.put("hibernate.show_sql", "true");
		return properties;
	}

	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/css/**").addResourceLocations("/resources/css/");
		registry.addResourceHandler("/js/**").addResourceLocations("/resources/js/");
		registry.addResourceHandler("/img/**").addResourceLocations("/resources/img/");
	}

}
