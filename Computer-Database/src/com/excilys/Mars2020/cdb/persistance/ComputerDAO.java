package com.excilys.Mars2020.cdb.persistance;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import com.excilys.Mars2020.cdb.model.Company;
import com.excilys.Mars2020.cdb.model.Computer;

/**
 * Class gathering computers interaction methods from database
 * @author remi
 *
 */
public class ComputerDAO {
	
	private static final String getAllComputersQuery = "SELECT name, id, introduced, discontinued, company_id from computer";
	private static final String getComputerDetailsQuery = "SELECT name, id, introduced, discontinued, company_id from computer WHERE id = ?";
	private static final String addComputerDB = "INSERT";
	private static final String updateComputerDB = "";
	private static final String deleteComputerDB = "";
	
	private ComputerDAO() {} //private constructor, forcing all methods to be static
	
	private static LocalDate timestampToLocalDate(Timestamp tmsp) {
		if(tmsp == null) { return null;} //en reflexion avec les types optionnals
		else { return tmsp.toLocalDateTime().toLocalDate(); }
	}
	
	/**
	 * Create an ArrayList of computers corresponding to the ResultSet argument
	 * @param resSet
	 * @return ArrayList with all the computers in resSet
	 * @throws SQLException
	 */
	private static ArrayList<Computer> storeComputersFromRequest(ResultSet resSet) throws SQLException{
		ArrayList<Computer> res = new ArrayList<Computer>();
		if(resSet != null) {
			while(resSet.next()) {
				Computer pc = new Computer.Builder(resSet.getString("name"))
						.id(resSet.getInt("id"))
						.introduced(ComputerDAO.timestampToLocalDate(resSet.getTimestamp("introduced")))
						.discontinued(ComputerDAO.timestampToLocalDate(resSet.getTimestamp("discontinued")))
						.companyId(resSet.getInt("company_id")).build();
				//System.out.println(pc);
				res.add(pc);
			}
		}
		return res;
	}
	
	public static ArrayList<Computer> getAllComputers() {
		ArrayList<Computer> res = new ArrayList<Computer>();
		try(MysqlConnection db = MysqlConnection.getDbConnection();
			PreparedStatement stmt = db.getConnect().prepareStatement(ComputerDAO.getAllComputersQuery);){
			ResultSet res1 = stmt.executeQuery();
			res = ComputerDAO.storeComputersFromRequest(res1);
		}catch (SQLException e){
			e.printStackTrace();
		}
		return res;
	}

}
