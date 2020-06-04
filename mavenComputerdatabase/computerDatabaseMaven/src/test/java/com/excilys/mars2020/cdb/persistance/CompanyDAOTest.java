package com.excilys.mars2020.cdb.persistance;

import static org.junit.Assert.assertNotEquals;

import java.io.FileInputStream;
import java.time.LocalDate;
import java.util.List;

import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Test;

import com.excilys.mars2020.cdb.model.Company;
import com.excilys.mars2020.cdb.model.Computer;

public class CompanyDAOTest extends DBTestCase {
	
	public CompanyDAOTest (String name) {
		super(name);
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "org.h2.Driver");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, "jdbc:h2:mem:test_db;DB_CLOSE_DELAY=-1;init=runscript from 'src/test/ressources/schema.sql'");
    }
	
	@Override
	protected IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(new FileInputStream("src/test/ressources/dataDBTesting.xml"));
    }
 
	@Override
    protected DatabaseOperation getSetUpOperation() throws Exception {
        return DatabaseOperation.REFRESH;
    }
 
	@Override
    protected DatabaseOperation getTearDownOperation() throws Exception {
        return DatabaseOperation.DELETE_ALL;
    }
    
	CompanyDAO myDAO = CompanyDAO.getCompanyDAO();
	Company dell = new Company.Builder().name("Dell").compId(1).build();
	Company lenovo = new Company.Builder().name("Lenovo").compId(2).build();
	Company apple = new Company.Builder().name("Apple").compId(3).build();
	Company fourth = new Company.Builder().compId(4).build();
	/*
	Computer pc1 = new Computer.Builder("pc1").pcId(1l).build();
	Computer pc2 = new Computer.Builder("pc2").pcId(2l).company(apple).build();
	Computer pc3 = new Computer.Builder("pc3").pcId(3l).company(lenovo).introduced(LocalDate.of(2018, 07, 24)).discontinued(LocalDate.of(2018, 9, 14)).build();
	Computer pc4 = new Computer.Builder("pc4").pcId(4l).introduced(LocalDate.of(2020, 01, 01)).discontinued(LocalDate.of(2022, 6, 18)).build();
	Computer pc5 = new Computer.Builder("pc5").pcId(5l).company(lenovo).build();
	
	@Test
	public void testGetALlCompaniesRequest() {
		List<Company> compList = myDAO.getAllCompaniesRequest();
		assertEquals(4, compList.size());
		assertTrue(compList.contains(dell));
		assertTrue(compList.contains(lenovo));
		assertTrue(compList.contains(apple));
		assertTrue(compList.contains(fourth));
	}
	
	@Test
	public void testGetOneCompanyRequest() {
		assertEquals(lenovo, myDAO.getOneCompanyRequest(2).get());
		assertEquals(fourth, myDAO.getOneCompanyRequest(4).get());
		assertNotEquals(apple, myDAO.getOneCompanyRequest(1).get());
	}
	
	@Test
	public void testCountAllCompanies() {
		assertEquals(4, myDAO.countAllCompanies());
		assertNotEquals(0, myDAO.countAllCompanies());
		//add 1 companies => Count should be 5
		//assertEquals(5, myDAO.countAllCompanies());
	}*/
}