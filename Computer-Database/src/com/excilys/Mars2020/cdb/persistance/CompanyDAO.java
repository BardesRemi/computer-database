package com.excilys.Mars2020.cdb.persistance;

import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

import com.excilys.Mars2020.cdb.model.Company;

/**
 * Class gathering companies query methods from database
 * @author remi
 *
 */
public class CompanyDAO {

	private static CompanyDAO compdao;
	
	private final String getAllCompaniesQuery = "SELECT id, name FROM company";
	private final String getOneCompaniesQuery = "SELECT id, name FROM company WHERE id = ?";
	
	private CompanyDAO() {} //private constructor, forcing all methods to be static
	
	public static synchronized CompanyDAO getCompanyDAO() {
		if(compdao == null) {
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
	private ArrayList<Company> storeCompaniesFromRequest(ResultSet resSet) throws SQLException{
		ArrayList<Company> res = new ArrayList<Company>();
		while(resSet.next()) {
			res.add(new Company(resSet.getString("name"),resSet.getInt("id")));
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
		if(resSet.next()) {
			return Optional.of(new Company(resSet.getString("name"),resSet.getInt("id")));
		}
		else {
			return Optional.empty();
		}
	}
	
	/**
	 * fetch all Companies from db and return them as an ArrayList
	 * @return ArrayList with all the companies
	 */
	public ArrayList<Company> getAllCompaniesRequest() {
		
		ArrayList<Company> res = new ArrayList<Company>();
		try(MysqlConnection db = MysqlConnection.getDbConnection();
			PreparedStatement stmt = db.getConnect().prepareStatement(compdao.getAllCompaniesQuery);){
			ResultSet res1 = stmt.executeQuery();
			res = compdao.storeCompaniesFromRequest(res1);
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return res;
	}
	
	/**
	 * get the company with id = id in the BD and retrieve it as Optional
	 * @param id
	 * @return Optional with the company, empty if doesn't exist
	 */
	
	
	public Optional<Company> getOneCompanyRequest(int id){
		try(MysqlConnection db = MysqlConnection.getDbConnection();
			PreparedStatement stmt = db.getConnect().prepareStatement(compdao.getOneCompaniesQuery);){
			stmt.setInt(1, id);
			ResultSet res1 = stmt.executeQuery();
			return compdao.storeOneCompanyFromRequest(res1);
		}			
		catch(SQLException e){
			e.printStackTrace();
		}
		return Optional.empty();
	}
}
