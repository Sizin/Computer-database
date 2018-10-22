package com.excilys.cdb.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

//@ComponentScan(basePackages ={"com.excilys.cdb.config", "com.excilys.cdb.mapper", "com.excilys.cdb.persistence",
//"com.excilys.cdb.services", "com.excilys.cdb.servlets", "com.excilys.cdb.app"})
public class MainWebAppInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(final ServletContext sc) throws ServletException {
		AnnotationConfigWebApplicationContext root = new AnnotationConfigWebApplicationContext();

		root.scan("com.excilys.cdb");
		sc.addListener(new ContextLoaderListener(root));

		ServletRegistration.Dynamic appServlet = sc.addServlet("mvc", new DispatcherServlet(new GenericWebApplicationContext()));
		appServlet.setLoadOnStartup(1);

	}

}
