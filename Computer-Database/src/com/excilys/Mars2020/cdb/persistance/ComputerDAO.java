package com.excilys.Mars2020.cdb.persistance;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLType;
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
	
	private static ComputerDAO pcdao;
	private final String getAllComputersQuery = "SELECT pc.name, pc.id, pc.introduced, pc.discontinued, pc.company_id, comp.name "
											  + "FROM computer AS pc "
											  + "LEFT JOIN company AS comp ON "
											  + "comp.id = pc.company_id";
	private final String getComputerDetailsQuery = "SELECT pc.name, pc.id, pc.introduced, pc.discontinued, pc.company_id, comp.name "
			  									 + "FROM computer AS pc "
			  									 + "LEFT JOIN company AS comp ON "
			  									 + "comp.id = pc.company_id "
			  									 + "WHERE pc.id = ?";
	private final String addNewComputerDB = "INSERT INTO computer (name,introduced,discontinued,company_id) VALUES ( ?, ?, ?, ?)";
	private final String updateComputerDB = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?";
	private final String deleteComputerDB = "DELETE FROM computer WHERE id = ?";
	
	private ComputerDAO() {} //private constructor, singleton
	
	public static synchronized ComputerDAO getComputerDAO() {
		if(pcdao == null) {
			pcdao = new ComputerDAO();
		}
		return pcdao;
	}
	
	private LocalDate timestampToLocalDate(Timestamp tmsp) {
		if(tmsp == null) { return null;} //en reflexion avec les types optionnals
		else { return tmsp.toLocalDateTime().toLocalDate(); }
	}
	
	/**
	 * Create an ArrayList of computers corresponding to the ResultSet argument
	 * @param resSet
	 * @return ArrayList with all the computers in resSet
	 * @throws SQLException
	 */
	private ArrayList<Computer> storeComputersFromRequest(ResultSet resSet) throws SQLException{
		ArrayList<Computer> res = new ArrayList<Computer>();
		while(resSet.next()) {
			Computer pc = new Computer.Builder(resSet.getString("pc.name"))
					.id(resSet.getInt("pc.id"))
					.introduced(pcdao.timestampToLocalDate(resSet.getTimestamp("pc.introduced")))
					.discontinued(pcdao.timestampToLocalDate(resSet.getTimestamp("pc.discontinued")))
					.company(new Company(resSet.getString("comp.name"), resSet.getInt("pc.company_id"))).build();
			res.add(pc);
			}
		return res;
	}
	
	/**
	 * Compute the request result for 1 computer (or none)
	 * @param resSet
	 * @return Optional with the request computer inside if it exist, empty if not
	 * @throws SQLException
	 */
	private Optional<Computer> storeOneOrNoneComputerFromReq(ResultSet resSet) throws SQLException{
		if(!resSet.next()) {
			return Optional.empty();
		}
		else {
			Computer pc = new Computer.Builder(resSet.getString("name"))
					.id(resSet.getInt("id"))
					.introduced(pcdao.timestampToLocalDate(resSet.getTimestamp("introduced")))
					.discontinued(pcdao.timestampToLocalDate(resSet.getTimestamp("discontinued")))
					.company(new Company(resSet.getString("comp.name"), resSet.getInt("pc.company_id"))).build();
			return Optional.of(pc);
		}
	}
	
	/**
	 * Create an ArrayList of Computers corresponding to all the registered Computer in the DB
	 * @return ArrayList with all the computers in computer-database
	 */
	public ArrayList<Computer> getAllComputers() {
		ArrayList<Computer> res = new ArrayList<Computer>();
		try(MysqlConnection db = MysqlConnection.getDbConnection();
			PreparedStatement stmt = db.getConnect().prepareStatement(pcdao.getAllComputersQuery);){
			ResultSet res1 = stmt.executeQuery();
			res = pcdao.storeComputersFromRequest(res1);
		}catch (SQLException e){
			e.printStackTrace();
		}
		return res;
	}
	
	/**
	 * search for a specifique computer in db, return it as optional if exist
	 * @param id
	 * @return Optional<Computer>
	 */
	public Optional<Computer> getOneComputers(int id) {
		try(MysqlConnection db = MysqlConnection.getDbConnection();
			PreparedStatement stmt = db.getConnect().prepareStatement(pcdao.getComputerDetailsQuery);){
			stmt.setInt(1, id);
			ResultSet res1 = stmt.executeQuery();
			return pcdao.storeOneOrNoneComputerFromReq(res1);
		}catch (SQLException e){
			e.printStackTrace();
		}
		return Optional.empty();
	}
	
	/**
	 * add a new PC in the db following the given informations
	 * @param pc
	 * @return the id associated to the new PC
	 */
	public int addNewComputer(Computer pc) {
		try(MysqlConnection db = MysqlConnection.getDbConnection();
			PreparedStatement stmt = db.getConnect().prepareStatement(pcdao.addNewComputerDB, PreparedStatement.RETURN_GENERATED_KEYS);){
			stmt.setString(1, pc.getName());
			LocalDate intro = pc.getIntroduce();
			stmt.setTimestamp(2, (intro == null ? null : Timestamp.valueOf(intro.atStartOfDay())));
			LocalDate discont = pc.getDiscontinued();
			stmt.setTimestamp(3, (discont == null ? null : Timestamp.valueOf(discont.atStartOfDay())));
			Company comp = pc.getcompany();
			if(comp == null) { stmt.setNull(4, java.sql.Types.INTEGER); }
			else { stmt.setInt(4, comp.getId());}
			stmt.executeUpdate();
			ResultSet resSet = stmt.getGeneratedKeys();
			if(resSet.next()) {return resSet.getInt(1);}
		} catch (SQLException e ) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * update a PC with id=id in the db
	 * @param id
	 * @param pc
	 * @return 1 if the update were done correctly, 0 otherwise
	 */
	public int updateComputer(int id, Computer pc) {
		try(MysqlConnection db = MysqlConnection.getDbConnection();
				PreparedStatement stmt = db.getConnect().prepareStatement(pcdao.updateComputerDB);){
				stmt.setInt(5, id);
				stmt.setString(1, pc.getName());
				LocalDate intro = pc.getIntroduce();
				stmt.setTimestamp(2, (intro == null ? null : Timestamp.valueOf(intro.atStartOfDay())));
				LocalDate discont = pc.getDiscontinued();
				stmt.setTimestamp(3, (discont == null ? null : Timestamp.valueOf(discont.atStartOfDay())));
				Company comp = pc.getcompany();
				if(comp == null) { stmt.setNull(4, java.sql.Types.INTEGER); }
				else { stmt.setInt(4, comp.getId());}
				return stmt.executeUpdate();
			} catch (SQLException e ) {
				e.printStackTrace();
			}
			return 0;
	}
	
	/**
	 * delete a specified computer in the db
	 * @param id
	 * @return number of row deleted (should be 1 or 0)
	 */
	public int deleteComputer (int id) {
		try(MysqlConnection db = MysqlConnection.getDbConnection();
			PreparedStatement stmt = db.getConnect().prepareStatement(pcdao.deleteComputerDB);){
			stmt.setInt(1, id);
			return stmt.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

}
