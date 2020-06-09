package com.excilys.mars2020.cdb.persistance;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.excilys.mars2020.cdb.mapper.DateMapper;
import com.excilys.mars2020.cdb.model.Company;
import com.excilys.mars2020.cdb.model.Computer;
import com.excilys.mars2020.cdb.model.Pagination;

/**
 * Class gathering computers interaction methods from database
 * @author remi
 *
 */
public class ComputerDAO {

	private static ComputerDAO pcdao;
	
	private static final String GET_ALL_COMPUTER_DETAILS_QUERY = "SELECT pc.name, pc.id, pc.introduced, pc.discontinued, pc.company_id, comp.name "
															   + "FROM computer AS pc "
															   + "LEFT JOIN company AS comp ON "
															   + "comp.id = pc.company_id";
	private static final String GET_COMPUTER_DETAILS_QUERY = "SELECT pc.name, pc.id, pc.introduced, pc.discontinued, pc.company_id, comp.name "
			  									 			+ "FROM computer AS pc "
			  									 			+ "LEFT JOIN company AS comp ON "
			  									 			+ "comp.id = pc.company_id "
			  									 			+ "WHERE pc.id = ?";
	private static final String ADD_NEW_COMPUTER_DB = "INSERT INTO computer (name,introduced,discontinued,company_id) VALUES ( ?, ?, ?, ?)";
	private static final String UPDATE_COMPUTER_DB = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?";
	private static final String DELETE_COMPUTER_DB = "DELETE FROM computer WHERE id = ?";
	private static final String COUNT_ALL_COMPUTERS_QUERY = "SELECT COUNT(id) AS rowcount FROM computer";
	private static final String GET_PAGE_COMPUTERS_QUERY = "SELECT pc.name, pc.id, pc.introduced, pc.discontinued, pc.company_id, comp.name "
											   		  	 + "FROM computer AS pc "
											   		  	 + "LEFT JOIN company AS comp ON "
											   		  	 + "comp.id = pc.company_id "
											   		  	 + "ORDER BY pc.id "
											   		  	 + "LIMIT ?, ?";
	
	private ComputerDAO() {} //private constructor, singleton
	
	public static synchronized ComputerDAO getComputerDAO() {
		if (pcdao == null) {
			pcdao = new ComputerDAO();
		}
		return pcdao;
	}
	
	/**
	 * Create an ArrayList of computers corresponding to the ResultSet argument
	 * @param resSet
	 * @return ArrayList with all the computers in resSet
	 * @throws SQLException
	 */
	private List<Computer> storeComputersFromRequest(ResultSet resSet) throws SQLException{
		List<Computer> res = new ArrayList<Computer>();
		while (resSet.next()) {
			Optional<LocalDate> introD = DateMapper.timestampToLocalDate(resSet.getTimestamp("pc.introduced"));
			Optional<LocalDate> discontD = DateMapper.timestampToLocalDate(resSet.getTimestamp("pc.discontinued"));
			Computer pc = new Computer.Builder(resSet.getString("pc.name"))
					.pcId(resSet.getLong("pc.id"))
					.introduced(introD.isEmpty() ? null : introD.get())
					.discontinued(discontD.isEmpty() ? null : discontD.get())
					.company(new Company.Builder().name(resSet.getString("comp.name")).compId(resSet.getInt("pc.company_id")).build()).build();
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
		if (resSet.next()) {
			Optional<LocalDate> introD = DateMapper.timestampToLocalDate(resSet.getTimestamp("pc.introduced"));
			Optional<LocalDate> discontD = DateMapper.timestampToLocalDate(resSet.getTimestamp("pc.discontinued"));
			Computer pc = new Computer.Builder(resSet.getString("name"))
					.pcId(resSet.getLong("id"))
					.introduced(introD.isEmpty() ? null : introD.get())
					.discontinued(discontD.isEmpty() ? null : discontD.get())
					.company(new Company.Builder().name(resSet.getString("name")).compId(resSet.getInt("id")).build()).build();
			return Optional.of(pc);
		}
		else {
			return Optional.empty();
		}
	}
	
	/**
	 * Create an ArrayList of Computers corresponding to all the registered Computer in the DB
	 * @return ArrayList with all the computers in computer-database
	 */
	public List<Computer> getAllComputersRequest() {
		List<Computer> res = new ArrayList<Computer>();
		try (MysqlConnection dbConnect = new MysqlConnection();
			PreparedStatement stmt = dbConnect.getConnect().prepareStatement(GET_ALL_COMPUTER_DETAILS_QUERY);) {
			ResultSet res1 = stmt.executeQuery();
			res = pcdao.storeComputersFromRequest(res1);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return res;
	}
	
	/**
	 * search for a specifique computer in db, return it as optional if exist
	 * @param id
	 * @return Optional<Computer>
	 */
	public Optional<Computer> getOneComputers(long id) {
		try (MysqlConnection dbConnect = new MysqlConnection();
			PreparedStatement stmt = dbConnect.getConnect().prepareStatement(GET_COMPUTER_DETAILS_QUERY);) {
			stmt.setLong(1, id);
			ResultSet res1 = stmt.executeQuery();
			return pcdao.storeOneOrNoneComputerFromReq(res1);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return Optional.empty();
	}
	
	/**
	 * add a new PC in the db following the given informations
	 * @param the pc to add in the db
	 * @return the id associated to the new PC
	 */
	public int insertNewComputer(Computer pc) {
		try (MysqlConnection dbConnect = new MysqlConnection();
			PreparedStatement stmt = dbConnect.getConnect().prepareStatement(ADD_NEW_COMPUTER_DB);) {
			stmt.setString(1, pc.getName());
			LocalDate intro = pc.getIntroduced();
			if(intro == null) {
				stmt.setNull(2, java.sql.Types.TIMESTAMP);
			}
			else {
				stmt.setTimestamp(2, DateMapper.localDateToTimestamp(intro).get());
			}
			LocalDate discont = pc.getDiscontinued();
			if(discont == null) {
				stmt.setNull(3, java.sql.Types.TIMESTAMP);
			}
			else {
				stmt.setTimestamp(3, DateMapper.localDateToTimestamp(discont).get());
			}
			Company comp = pc.getcompany();
			if (comp == null) {
				stmt.setNull(4, java.sql.Types.INTEGER); 
			}
			else {
				stmt.setLong(4, comp.getCompId());
			}
			return stmt.executeUpdate();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * update a PC with pc.id= computer.id in the db
	 * @param pc
	 * @return 1 if the update were done correctly, 0 otherwise
	 */
	public int updateComputer(Computer pc) {
		try (MysqlConnection dbConnect = new MysqlConnection();
				PreparedStatement stmt = dbConnect.getConnect().prepareStatement(UPDATE_COMPUTER_DB);){
				stmt.setLong(5, pc.getPcId());
				stmt.setString(1, pc.getName());
				LocalDate intro = pc.getIntroduced();
				stmt.setTimestamp(2, (intro == null ? null : DateMapper.localDateToTimestamp(intro)).get());
				LocalDate discont = pc.getDiscontinued();
				stmt.setTimestamp(3, (discont == null ? null : DateMapper.localDateToTimestamp(discont)).get());
				Company comp = pc.getcompany();
				if (comp == null) {
					stmt.setNull(4, java.sql.Types.INTEGER);
				}
				else {
					stmt.setLong(4, comp.getCompId());
				}
				return stmt.executeUpdate();
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}
			return 0;
	}
	
	/**
	 * delete a specified computer in the db
	 * @param id
	 * @return number of row deleted (should be 1 or 0)
	 */
	public int deleteComputer (long id) {
		try (MysqlConnection dbConnect = new MysqlConnection();
			PreparedStatement stmt = dbConnect.getConnect().prepareStatement(DELETE_COMPUTER_DB);) {
			stmt.setLong(1, id);
			return stmt.executeUpdate();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * Get the number of different PC in computer
	 * @return the number of PC
	 */
	public int countAllComputer() {
		try (MysqlConnection dbConnect = new MysqlConnection();
			PreparedStatement stmt = dbConnect.getConnect().prepareStatement(COUNT_ALL_COMPUTERS_QUERY);) {
			ResultSet res1 = stmt.executeQuery();
			if (res1.next()) {
				return res1.getInt("rowcount");
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * Create an ArrayList of Computers corresponding to the registered Computer in the DB in the page range
	 * @return ArrayList with the computers in computer-database
	 */
	public List<Computer> getPageComputersRequest(Pagination page) {
		List<Computer> res = new ArrayList<Computer>();
		try (MysqlConnection dbConnect = new MysqlConnection();
			PreparedStatement stmt = dbConnect.getConnect().prepareStatement(GET_PAGE_COMPUTERS_QUERY);) {
			stmt.setInt(1, page.getActualPageNb() * page.getPageSize());
			stmt.setInt(2, page.getPageSize());
			ResultSet res1 = stmt.executeQuery();
			res = pcdao.storeComputersFromRequest(res1);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return res;
	}

}
