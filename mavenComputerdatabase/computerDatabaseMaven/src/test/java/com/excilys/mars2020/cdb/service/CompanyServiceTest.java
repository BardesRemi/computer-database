package com.excilys.mars2020.cdb.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.excilys.mars2020.cdb.mapper.Mapper;
import com.excilys.mars2020.cdb.model.Company;
import com.excilys.mars2020.cdb.model.CompanyDTO;
import com.excilys.mars2020.cdb.persistance.CompanyDAO;

public class CompanyServiceTest {
	@Mock 
	CompanyDAO MockDAO = Mockito.mock(CompanyDAO.class);
	@InjectMocks 
	CompanyService myService = new CompanyService(MockDAO);
	ArrayList<Company> compList;
	Company dellId0 = new Company.Builder().name("Dell").compId(0).build();
	Company dellId10 = new Company.Builder().name("Dell").compId(10).build();
	Company lenovoId1 = new Company.Builder().name("Lenovo").compId(1).build();
	Company id2 = new Company.Builder().compId(2).build();
	
	private void refreshMocks() {
		mockGetOneCompanyRequest();
		mockGetCountCompanies();
		mockGetAllCompanies();
	}
	
	private void mockGetOneCompanyRequest () {
		Mockito.when(MockDAO.getOneCompanyRequest(Mockito.anyLong())).thenReturn(Optional.empty());
		Mockito.when(MockDAO.getOneCompanyRequest(0)).thenReturn(Optional.of(compList.get(0)));
		Mockito.when(MockDAO.getOneCompanyRequest(1)).thenReturn(Optional.of(compList.get(1)));
		Mockito.when(MockDAO.getOneCompanyRequest(2)).thenReturn(Optional.of(compList.get(2)));
	}
	
	private void mockGetCountCompanies() {
		Mockito.when(MockDAO.countAllCompanies()).thenReturn(compList.size());
	}
	
	private void mockGetAllCompanies() {
		Mockito.when(MockDAO.getAllCompaniesRequest()).thenReturn(compList);
	}
	
	
	@Before
	public void initialize() {
		compList = new ArrayList<Company>();
		compList.add(dellId0);
		compList.add(lenovoId1);
		compList.add(id2);
		
		this.refreshMocks();
	}
	
	@Test
	public void companyInDbTest() {
		
		assertTrue(myService.companyInDb(dellId0));
		assertTrue(myService.companyInDb(lenovoId1));
		assertFalse(myService.companyInDb(dellId10));
		assertTrue(myService.companyInDb(id2));
	}
	
	@Test
	public void getAllCompaniesTest() {
		List<CompanyDTO> testResult = myService.getAllCompanies();
		
		assertTrue(testResult.contains(Mapper.companyToCompanyDTO(dellId0)));
		assertTrue(testResult.contains(Mapper.companyToCompanyDTO(lenovoId1)));
		assertTrue(testResult.contains(Mapper.companyToCompanyDTO(id2)));
		assertTrue(testResult.size()==3);
	}
	
	@Test
	public void getCountCompanies() {
		assertTrue(myService.getCountCompanies()==3);
	}
}