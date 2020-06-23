package com.excilys.mars2020.cdb.validations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.mars2020.cdb.exceptions.LogicalProblem;
import com.excilys.mars2020.cdb.mapper.DateMapper;
import com.excilys.mars2020.cdb.mapper.Mapper;
import com.excilys.mars2020.cdb.service.CompanyService;
import com.excilys.mars2020.cdb.service.ComputerService;
import com.excilys.mars2020.cdb.spring.SpringConfig;

public class LogicalCheckerTest {
	
	private static AnnotationConfigApplicationContext appContext = SpringConfig.getContext();
	
	private LogicalChecker logicCheck = appContext.getBean(LogicalChecker.class);
	
	@Test
	public void idGivenChecking () {
		
	}
	
	@Test
	public void dateValidationCheckingTests() {
		LocalDate introValid = LocalDate.of(2017, 06, 01);
		LocalDate start2020 = LocalDate.of(2020, 01, 01);
		assertTrue(logicCheck.dateValidationChecking(introValid, start2020).isEmpty());
		assertTrue(logicCheck.dateValidationChecking(null, start2020).isEmpty());
		assertTrue(logicCheck.dateValidationChecking(introValid, null).isEmpty());
		assertTrue(logicCheck.dateValidationChecking(null, null).isEmpty());
		assertTrue(logicCheck.dateValidationChecking(start2020, introValid).isPresent());
		assertEquals(logicCheck.dateValidationChecking(start2020, introValid), 
				     Optional.of(LogicalProblem.createInvalidDatesProblem(start2020.toString() + " is before " + introValid.toString())));
	}
	
	@Test
	public void companyIsUnknownChecking () {
		
	}
	
	
	
	
}
