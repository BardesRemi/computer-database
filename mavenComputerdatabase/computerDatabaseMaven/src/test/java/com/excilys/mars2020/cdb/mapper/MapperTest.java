package com.excilys.mars2020.cdb.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

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
	
	@Test
	public void companyDTOToCompanyTest() {
		
	}
	
	@Test
	public void computerDTOToComputerTest() {
		
	}

}
