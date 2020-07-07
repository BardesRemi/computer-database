package com.excilys.mars2020.cdb.spring;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class WebAppInitializer implements WebApplicationInitializer {
	
	@Override
	public void onStartup(final ServletContext sc) throws ServletException {
		
		AnnotationConfigWebApplicationContext root = new AnnotationConfigWebApplicationContext();
		root.register(SpringConfig.class, WebConfig.class);
		root.setServletContext(sc);
		
		sc.addListener(new ContextLoaderListener(root));
		
		ServletRegistration.Dynamic appServlet = sc.addServlet("dispatcher", new DispatcherServlet(root));
		appServlet.setLoadOnStartup(1);
		appServlet.addMapping("/");
	}
}
