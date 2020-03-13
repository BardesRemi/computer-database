package com.excilys.mars2020.cdb.persistance;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.excilys.mars2020.cdb.model.Company;
import com.excilys.mars2020.cdb.model.Pagination;

/**
 * Class gathering companies query methods from database
 * @author remi
 *
 */
public class CompanyDAO {
	/*
	 * GET_ALL_COMPAGNIES_QUERY
	 * GET_ONE_COMPANY_QUERY
	 * COUNT_ALL_COMPANIES_QUERY
	 * GET_PAGE_COMPANIES_QUERY
	 */

	private static CompanyDAO compdao;
	
	private static final String GET_ALL_COMPAGNIES_QUERY = "SELECT id, name FROM company";
	private static final String GET_ONE_COMPANY_QUERY = "SELECT id, name FROM company WHERE id = ?";
	private static final String COUNT_ALL_COMPANIES_QUERY = "SELECT COUNT(id) AS rowcount FROM company";
	private static final String GET_PAGE_COMPANIES_QUERY = "SELECT id, name FROM company ORDER BY id LIMIT ?, ?";
	
	private CompanyDAO() {}
	
	public static synchronized CompanyDAO getCompanyDAO() {
		if (compdao == null) {
			compdao = new CompanyDAO();
		}
		return compdao;
	}
	
	/**
	 * Create an ArrayList of companies corresponding to the ResultSet argument
	 * @param resSet
	 * @return ArrayList with all the companies
	 * @throws SQLException
	 */
	private List<Company> storeCompaniesFromRequest(ResultSet resSet) throws SQLException{
		List<Company> res = new ArrayList<Company>();
		while (resSet.next()) {
			res.add( new Company.Builder().name(resSet.getString("name")).compId(resSet.getInt("id")).build());
		}
		return res;
	}
	
	/**
	 * Create an Optional with the company searched by the request
	 * @param resSet
	 * @return The company or empty if no result
	 * @throws SQLException
	 */
	private Optional<Company> storeOneCompanyFromRequest(ResultSet resSet) throws SQLException{
		if (resSet.next()) {
			return Optional.of(new Company.Builder().name(resSet.getString("name")).compId(resSet.getInt("id")).build());
		}
		else {
			return Optional.empty();
		}
	}
	
	/**
	 * fetch all Companies from db and return them as an ArrayList
	 * @return ArrayList with all the companies
	 */
	public List<Company> getAllCompaniesRequest() {
		
		List<Company> res = new ArrayList<Company>();
		try (MysqlConnection dbConnect = new MysqlConnection();
			PreparedStatement stmt = dbConnect.getConnect().prepareStatement(GET_ALL_COMPAGNIES_QUERY);) {
			ResultSet res1 = stmt.executeQuery();
			res = compdao.storeCompaniesFromRequest(res1);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return res;
	}
	
	/**
	 * get the company with id = id in the BD and retrieve it as Optional
	 * @param id
	 * @return Optional with the company, empty if doesn't exist
	 */
	public Optional<Company> getOneCompanyRequest(long id){
		try (MysqlConnection dbConnect = new MysqlConnection();
			PreparedStatement stmt = dbConnect.getConnect().prepareStatement(GET_ONE_COMPANY_QUERY);) {
			stmt.setLong(1, id);
			ResultSet res1 = stmt.executeQuery();
			return compdao.storeOneCompanyFromRequest(res1);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return Optional.empty();
	}
	
	/**
	 * Get the number of different Companies in company
	 * @return the number of companies
	 */
	public int countAllCompanies() {
		try (MysqlConnection dbConnect = new MysqlConnection();
			PreparedStatement stmt = dbConnect.getConnect().prepareStatement(COUNT_ALL_COMPANIES_QUERY);) {
			ResultSet res1 = stmt.executeQuery();
			if(res1.next()) {
				return res1.getInt("rowcount");
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * Create an ArrayList of Companies corresponding to the registered Companies in the DB in the page range
	 * @return ArrayList with the Companies in computer-database
	 */
	public List<Company> getPageCompaniesRequest(Pagination page) {
		List<Company> res = new ArrayList<Company>();
		try (MysqlConnection dbConnect = new MysqlConnection();
			PreparedStatement stmt = dbConnect.getConnect().prepareStatement(GET_PAGE_COMPANIES_QUERY);) {
			stmt.setInt(1, page.getActualPageNb()*page.getPageSize());
			stmt.setInt(2, page.getPageSize());
			ResultSet res1 = stmt.executeQuery();
			res = compdao.storeCompaniesFromRequest(res1);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return res;
	}
}
