package com.excilys.mars2020.cdb.persistance;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.excilys.mars2020.cdb.model.Company;
import com.excilys.mars2020.cdb.model.Computer;
import com.excilys.mars2020.cdb.model.Pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Class gathering companies query methods from database
 * @author remi
 *
 */
@Repository
public class CompanyDAO {
	

	
	@Autowired
	private static ComputerDAO pcdao;
	
	private static final String GET_ALL_COMPAGNIES_QUERY = "SELECT id, name FROM company";
	private static final String GET_ONE_COMPANY_QUERY = "SELECT id, name FROM company WHERE id = :compId";
	private static final String COUNT_ALL_COMPANIES_QUERY = "SELECT COUNT(id) AS rowcount FROM company";
	private static final String GET_PAGE_COMPANIES_QUERY = "SELECT id, name FROM company ORDER BY id LIMIT :start, :qty";
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	@Autowired
	private CompanyRawMapper compRawMapper;
	
	private CompanyDAO() {}
	
	/**
	 * fetch all Companies from db and return them as an ArrayList
	 * @return ArrayList with all the companies
	 */
	public List<Company> getAllCompaniesRequest() {
		
		try {
			return jdbcTemplate.query(GET_ALL_COMPAGNIES_QUERY, compRawMapper);
		} catch (DataAccessException dataE) {
			dataE.printStackTrace();
			return Collections.emptyList();
		}
	}
	
	/**
	 * get the company with id = id in the BD and retrieve it as Optional
	 * @param id
	 * @return Optional with the company, empty if doesn't exist
	 */
	public Optional<Company> getOneCompanyRequest(long id){
		try {
			MapSqlParameterSource paramMap = new MapSqlParameterSource().addValue("compId", id);
			List<Company> res = jdbcTemplate.query(GET_ONE_COMPANY_QUERY, paramMap, compRawMapper);
			if(res.isEmpty()) {
				return Optional.empty();
			} else {
				return Optional.of(res.get(0));
			}
		} catch (DataAccessException dataE) {
			dataE.printStackTrace();
			return Optional.empty();
		}
	}
	
	/**
	 * Get the number of different Companies in company
	 * @return the number of companies
	 */
	public int countAllCompanies() {
		try {
			return jdbcTemplate.queryForObject(COUNT_ALL_COMPANIES_QUERY, new MapSqlParameterSource(), Integer.class);
		} catch (DataAccessException dataE) {
			dataE.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * Create an ArrayList of Companies corresponding to the registered Companies in the DB in the page range
	 * @return ArrayList with the Companies in computer-database
	 */
	public List<Company> getPageCompaniesRequest(Pagination page) {
		try {
			MapSqlParameterSource paramMap = new MapSqlParameterSource()
											.addValue("start", page.getActualPageNb() * page.getPageSize())
											.addValue("qty", page.getPageSize());
			return jdbcTemplate.query(GET_PAGE_COMPANIES_QUERY, paramMap, compRawMapper);
		} catch(DataAccessException dataE) {
			dataE.printStackTrace();
			return Collections.emptyList();
		}
	}
	
	@Transactional
	public int deleteCompany(long id) {
		List<Computer> pcToDeleteList = pcdao.getComputerByCompanyId(id);
		//delete all pc in pcToDeleteList then the given company with transactions to keep the ACID idea
		try {
			for(Computer pc : pcToDeleteList) { pcdao.deleteComputer(pc.getPcId());}
			MapSqlParameterSource paramMap = new MapSqlParameterSource().addValue("company_id", id);
			return jdbcTemplate.update("DELETE FROM company WHERE id = :company_id", paramMap);
		} catch (DataAccessException dataE) {
			dataE.printStackTrace();
			return 0;
		}
	}
}
