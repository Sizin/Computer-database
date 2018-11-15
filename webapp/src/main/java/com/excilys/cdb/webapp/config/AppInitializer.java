package com.excilys.cdb.webapp.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.excilys.cdb.webapp.security.SecurityConfig;

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
	
    @Override
    protected Class<?>[] getRootConfigClasses() {
//        return new Class<?>[] { HibernateAppConfig.class, SecurityConfig.class };
//    	return new Class<?>[] { HibernateAppConfig.class};
    	return new Class<?>[]  {SecurityConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] { WebMvcConfig.class };
    }
    
    @Override
    protected String[] getServletMappings() {
		return new String[] {"/"};
}
}
