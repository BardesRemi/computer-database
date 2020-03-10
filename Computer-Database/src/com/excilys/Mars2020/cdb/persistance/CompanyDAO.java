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
	private final String getAllCompaniesQuery = "SELECT id, name from company";
	
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
		if(resSet != null) {
			while(resSet.next()) {
				res.add(new Company(resSet.getString("name"),resSet.getInt("id")));
			}
		}
		return res;
	}
	
	/**
	 * fetch all Companies from db and return them as an ArrayList
	 * @return ArrayList with all the companies
	 */
	public ArrayList<Company> getAllCompanies() {
		
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
}
