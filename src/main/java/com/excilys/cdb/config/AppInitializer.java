package com.excilys.cdb.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer{
	
	@Override
	protected Class<?>[] getRootConfigClasses(){
		return new Class<?>[] {HikariAppConfig.class};
	}
	
	@Override
	protected Class<?>[] getServletConfigClasses(){
		return new Class<?>[] {WebMvcConfig.class};
	}
	
	@Override
	protected String[] getServletMappings(){
		return new String[] {"/"};
	}


}
