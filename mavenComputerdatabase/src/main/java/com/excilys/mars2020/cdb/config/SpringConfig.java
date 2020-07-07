package com.excilys.mars2020.cdb.config;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(value = { DaoConfig.class, ServiceConfig.class, WebConfig.class })
public class SpringConfig {
	public static AnnotationConfigApplicationContext getContext() {
		return null;
	}
}
