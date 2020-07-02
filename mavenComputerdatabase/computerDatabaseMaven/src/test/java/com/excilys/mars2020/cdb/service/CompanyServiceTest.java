package com.excilys.mars2020.cdb.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.excilys.mars2020.cdb.exceptions.ParseExceptions;
import com.excilys.mars2020.cdb.mapper.CompanyMapper;
import com.excilys.mars2020.cdb.mapper.Mapper;
import com.excilys.mars2020.cdb.model.Company;
import com.excilys.mars2020.cdb.model.CompanyDTO;
import com.excilys.mars2020.cdb.persistence.CompanyDAO;

@RunWith (MockitoJUnitRunner.class)
public class CompanyServiceTest {
	@Mock 
	CompanyDAO MockDAO;
	
	@Mock
	CompanyMapper compMapper;
	
	@Mock
	Mapper mapper;
	
	@InjectMocks 
	CompanyService myService;
	
	private static ArrayList<Company> compList;
	Company dellId0 = new Company.Builder().name("Dell").compId(0).build();
	Company dellId10 = new Company.Builder().name("Dell").compId(10).build();
	Company lenovoId1 = new Company.Builder().name("Lenovo").compId(1).build();
	Company id2 = new Company.Builder().compId(2).build();
	
	CompanyDTO dellId0DTO = new CompanyDTO.Builder().name("Dell").compId("0").build();
	CompanyDTO dellId10DTO = new CompanyDTO.Builder().name("Dell").compId("10").build();
	CompanyDTO lenovoId1DTO = new CompanyDTO.Builder().name("Lenovo").compId("1").build();
	CompanyDTO id2DTO = new CompanyDTO.Builder().compId("2").build();
	
	private void refreshMocks() {
		mockGetOneCompanyRequest();
		mockGetCountCompanies();
		mockGetAllCompanies();
		mockCompanyDTOToCompany();
		mockCompanyToCompanyDTO();
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
	
	private void mockCompanyDTOToCompany() {
		try {
			Mockito.when(compMapper.companyDTOToCompany(dellId0DTO)).thenReturn(Optional.of(dellId0));
			Mockito.when(compMapper.companyDTOToCompany(lenovoId1DTO)).thenReturn(Optional.of(lenovoId1));
			Mockito.when(compMapper.companyDTOToCompany(id2DTO)).thenReturn(Optional.of(id2));
			Mockito.when(compMapper.companyDTOToCompany(dellId10DTO)).thenReturn(Optional.of(dellId10));
		} catch (ParseExceptions e) {
			e.printStackTrace();
		}
	}
	
	private void mockCompanyToCompanyDTO() {
		Mockito.when(compMapper.companyToCompanyDTO(dellId0)).thenReturn(dellId0DTO);
		Mockito.when(compMapper.companyToCompanyDTO(lenovoId1)).thenReturn(lenovoId1DTO);
		Mockito.when(compMapper.companyToCompanyDTO(id2)).thenReturn(id2DTO);
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
		
		assertTrue(myService.companyInDb(dellId0DTO));
		assertTrue(myService.companyInDb(lenovoId1DTO));
		assertFalse(myService.companyInDb(dellId10DTO));
		assertTrue(myService.companyInDb(id2DTO));
	}
	
	@Test
	public void getAllCompaniesTest() {
		
		List<CompanyDTO> testResult = myService.getAllCompanies();
		
		assertTrue(testResult.contains(dellId0DTO));
		assertTrue(testResult.contains(lenovoId1DTO));
		assertTrue(testResult.contains(id2DTO));
		assertTrue(testResult.size()==3);
	}
	
	@Test
	public void getCountCompanies() {
		Mockito.when(MockDAO.countAllCompanies()).thenReturn(compList.size());
		assertTrue(myService.getCountCompanies()==3);
	}
}