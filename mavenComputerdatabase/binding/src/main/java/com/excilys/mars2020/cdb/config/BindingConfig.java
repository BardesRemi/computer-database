package com.excilys.mars2020.cdb.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.excilys.mars2020.cdb.exceptions", "com.excilys.mars2020.mapper" } )
public class BindingConfig {}
