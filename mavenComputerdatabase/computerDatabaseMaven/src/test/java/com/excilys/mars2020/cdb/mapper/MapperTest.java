package com.excilys.mars2020.cdb.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.junit.Test;

import com.excilys.mars2020.cdb.exceptions.ParseExceptions;
import com.excilys.mars2020.cdb.model.*;

public class MapperTest {

	@Test
	public void stringToLongTest() {
		Long nb3l = 3l, nb0l = 0l;
		assertEquals(nb0l, Mapper.stringToLong("").get());
		assertEquals(nb0l, Mapper.stringToLong(null).get());
		assertTrue(Mapper.stringToLong("10,45").isEmpty());
		assertTrue(Mapper.stringToLong("12.6543").isEmpty());
		assertEquals(nb3l, Mapper.stringToLong("3").get());
	}
	
	@Test(expected = ParseExceptions.class)
	public void companyDTOToCompanyExceptionTest() throws ParseExceptions {
		CompanyDTO compDTO1 = new CompanyDTO.Builder().compId("Aloa").name("aaa").build();
		Company comp = Mapper.companyDTOToCompany(compDTO1).get();
	}
	
	@Test
	public void companyDTOToCompanyTest() {
		CompanyDTO compDTONoName = new CompanyDTO.Builder().compId("1337").build();
		CompanyDTO compDTONoId = new CompanyDTO.Builder().name("emptyId").build();
		Company compNoName = new Company.Builder().compId(1337).build();
		Company comp1 = new Company.Builder().compId(10).name("aaa").build();
		Company comp2 = new Company.Builder().compId(11).name("bbb").build();
		CompanyDTO compDTO1 = new CompanyDTO.Builder().compId("10").name("aaa").build();
		try {
			assertTrue(Mapper.companyDTOToCompany(compDTONoId).isEmpty());
			assertTrue(Mapper.companyDTOToCompany(compDTONoName).isPresent());
			assertEquals(compNoName, Mapper.companyDTOToCompany(compDTONoName).get());
			assertEquals(comp1, Mapper.companyDTOToCompany(compDTO1).get());
			assertNotEquals(comp2, Mapper.companyDTOToCompany(compDTO1).get());
		} catch (ParseExceptions e) {
			System.out.println("Exception in companyDTOToCompanyTest, shouldn't happen");
			e.getMessage();
		}
	}
	
	@Test
	public void companyToCompanyDTOTest() {
		Company comp1 = new Company.Builder().compId(10l).name("aaa").build();
		Company comp2 = new Company.Builder().compId(11l).name("bbb").build();
		CompanyDTO compDTO1 = new CompanyDTO.Builder().compId("10").name("aaa").build();
		assertEquals(compDTO1, Mapper.companyToCompanyDTO(comp1));
		assertNotEquals(compDTO1, Mapper.companyToCompanyDTO(comp2));
	}
	
	@Test
	public void computerDTOToComputerTest() {
		Company comp1 = new Company.Builder().compId(10l).name("aaa").build();
		Company comp2 = new Company.Builder().compId(11l).name("bbb").build();
		Computer pc1 = new Computer.Builder("pc1").pcId(0l).build();
		Computer pc2 = new Computer.Builder("pc2").pcId(1l).company(comp1).build();
		Computer pc3 = new Computer.Builder("pc3").pcId(2l).company(comp2).build();
		Computer pc4 = new Computer.Builder("pc4").pcId(3l).introduced(LocalDate.of(2020, 01, 01)).discontinued(LocalDate.of(2022, 6, 18)).build();
		
		CompanyDTO compDTO1 = new CompanyDTO.Builder().compId("10").name("aaa").build();
		ComputerDTO pcDTO1 = new ComputerDTO.Builder("pc1").pcId("0").build();
		ComputerDTO pcDTO1Bis = new ComputerDTO.Builder("pc1").pcId("0").company(compDTO1).build();
		ComputerDTO pcDTO2 = new ComputerDTO.Builder("pc2").pcId("1").company(compDTO1).build();
		ComputerDTO pcDTO3 = new ComputerDTO.Builder("pc4").pcId("3").introduced("01/01/2020").discontinued("18/06/2022").build();
		
		try {
			assertEquals(pc1, Mapper.ComputerDTOToComputer(pcDTO1));
			assertEquals(pc2, Mapper.ComputerDTOToComputer(pcDTO2));
			assertNotEquals(pc1, Mapper.ComputerDTOToComputer(pcDTO1Bis));
			assertNotEquals(pc3, Mapper.ComputerDTOToComputer(pcDTO2));
			assertEquals(pc4, Mapper.ComputerDTOToComputer(pcDTO3));
		} catch (ParseExceptions e) {
			System.out.println("Exception in computerDTOToComputerTest , shouldn't happen");
			e.printStackTrace();
		}
	}
	
	@Test
	public void computerToComputerDTOTest() {
		Company comp1 = new Company.Builder().compId(10l).name("aaa").build();
		Computer pc1 = new Computer.Builder("pc1").pcId(0l).build();
		Computer pc2 = new Computer.Builder("pc2").pcId(1l).company(comp1).build();
		Computer pc4 = new Computer.Builder("pc4").pcId(3l).introduced(LocalDate.of(2020, 01, 01)).discontinued(LocalDate.of(2022, 6, 18)).build();
		CompanyDTO compDTO1 = new CompanyDTO.Builder().compId("10").name("aaa").build();
		ComputerDTO pcDTO1 = new ComputerDTO.Builder("pc1").pcId("0").build();
		ComputerDTO pcDTO1Bis = new ComputerDTO.Builder("pc1").pcId("0").company(compDTO1).build();
		ComputerDTO pcDTO2 = new ComputerDTO.Builder("pc2").pcId("1").company(compDTO1).build();
		ComputerDTO pcDTO3 = new ComputerDTO.Builder("pc4").pcId("3").introduced("01/01/2020").discontinued("18/06/2022").build();
		assertEquals(pcDTO1, Mapper.computerToComputerDTO(pc1));
		assertNotEquals(pcDTO1Bis, Mapper.computerToComputerDTO(pc1));
		assertEquals(pcDTO2, Mapper.computerToComputerDTO(pc2));
		assertEquals(pcDTO3, Mapper.computerToComputerDTO(pc4));
	}

}
