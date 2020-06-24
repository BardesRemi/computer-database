package com.excilys.mars2020.cdb.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

@Configuration
@ComponentScan(basePackages = "com.excilys.mars2020.cdb")
public class SpringConfig {
		
	private static AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext (SpringConfig.class);
	
	public static AnnotationConfigApplicationContext getContext() {
		return appContext;
	}
	
	@Bean(destroyMethod = "close")
	public HikariDataSource HikariDataSource() {
		return new HikariDataSource(new HikariConfig("/db.properties"));
	}
	
	@Bean
	NamedParameterJdbcTemplate namedParameterJdbcTemplate(HikariDataSource datasource) {
		return new NamedParameterJdbcTemplate(datasource);
	}
}
