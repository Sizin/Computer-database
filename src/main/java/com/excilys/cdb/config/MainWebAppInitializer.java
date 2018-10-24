package com.excilys.cdb.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import org.springframework.web.servlet.DispatcherServlet;

public class MainWebAppInitializer implements WebApplicationInitializer {

	// uncomment to run the student controller example
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
		applicationContext.register(WebConfig.class);
		applicationContext.setServletContext(servletContext);
		servletContext.addListener(new ContextLoaderListener(applicationContext));

		DispatcherServlet dispatcher = new DispatcherServlet(applicationContext);

		ServletRegistration.Dynamic appServlet = servletContext.addServlet("CdbSinan", dispatcher);
		appServlet.setLoadOnStartup(1);
		appServlet.addMapping("/");
	}
}
