package com.excilys.mars2020.cdb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

		@Autowired
		private UserDetailsService customUserDetailService;
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable()
				.authorizeRequests().antMatchers("/").permitAll().anyRequest().authenticated()
				.and().formLogin().loginPage("/WEB-INF/view/login").defaultSuccessUrl("/WEB-INF/view/dashboard")
																	   .failureUrl("/WEB-INF/view/login?error=true")
				.and().authorizeRequests().antMatchers("/WEB-INF/view/dashboard").hasAnyRole("USER","ADMIN")
				.and().authorizeRequests().antMatchers("/WEB-INF/view/edit", "/WEB-INF/view/delete", "/WEB-INF/view/add")
										  .hasRole("ADMIN")
				.and().logout().logoutUrl("/WEB-INF/view/login?logout=true");
		}
		
		@Override
		protected void configure(AuthenticationManagerBuilder authManagerBuilder) throws Exception {
			authManagerBuilder.userDetailsService(customUserDetailService).passwordEncoder(bCryptPasswordEncoder());
		}
		
		@Bean
		public BCryptPasswordEncoder bCryptPasswordEncoder() {
			return new BCryptPasswordEncoder();
		}
		
		@Bean
		@Override
		public AuthenticationManager authenticationManagerBean() throws Exception {
			return super.authenticationManagerBean();
		}
	
}
