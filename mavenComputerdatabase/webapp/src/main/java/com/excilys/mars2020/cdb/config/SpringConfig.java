package com.excilys.mars2020.cdb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(value = { DaoConfig.class, ServiceConfig.class, WebConfig.class, BindingConfig.class,
				  WebSecurityConfig.class})
public class SpringConfig {}
