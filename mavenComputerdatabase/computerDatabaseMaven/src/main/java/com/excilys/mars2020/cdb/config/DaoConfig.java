package com.excilys.mars2020.cdb.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import org.springframework.context.annotation.Bean;

@Configuration
@ComponentScan(basePackages = {"com.excilys.mars2020.cdb.persistence" } )
public class DaoConfig {
	
	@Bean(destroyMethod = "close")
	public HikariDataSource HikariDataSource() {
		return new HikariDataSource(new HikariConfig("/db.properties"));
	}
	
	@Bean
	public NamedParameterJdbcTemplate namedParameterJdbcTemplate(HikariDataSource datasource) {
		return new NamedParameterJdbcTemplate(datasource);
	}
	
}