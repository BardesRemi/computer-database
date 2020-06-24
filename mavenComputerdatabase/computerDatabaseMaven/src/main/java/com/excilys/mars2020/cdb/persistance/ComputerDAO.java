package com.excilys.mars2020.cdb.persistance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.mars2020.cdb.mapper.DateMapper;
import com.excilys.mars2020.cdb.model.Company;
import com.excilys.mars2020.cdb.model.Computer;
import com.excilys.mars2020.cdb.model.Pagination;
import com.excilys.mars2020.cdb.spring.SpringConfig;

/**
 * Class gathering computers interaction methods from database
 * @author remi
 *
 */
@Repository
public class ComputerDAO {
	
	private static final String GET_ALL_COMPUTER_DETAILS_QUERY = "SELECT pc.name, pc.id, pc.introduced, pc.discontinued, pc.company_id, comp.name "
															   + "FROM computer AS pc "
															   + "LEFT JOIN company AS comp ON "
															   + "comp.id = pc.company_id";
	private static final String GET_COMPUTER_BY_NAME_QUERY = "SELECT pc.name, pc.id, pc.introduced, pc.discontinued, pc.company_id, comp.name "
			   												+ "FROM computer AS pc "
			   												+ "LEFT JOIN company AS comp ON "
			   												+ "comp.id = pc.company_id "
															+ "WHERE pc.name LIKE :name "
			   												+ "OR comp.name LIKE :name";
	private static final String GET_COMPUTER_BY_COMPANY_ID_QUERY = "SELECT pc.name, pc.id, pc.introduced, pc.discontinued, pc.company_id, comp.name "
																	+ "FROM computer AS pc "
																	+ "LEFT JOIN company AS comp ON "
																	+ "comp.id = pc.company_id "
																	+ "WHERE comp.id = :company_id ";
	private static final String GET_COMPUTER_DETAILS_QUERY = "SELECT pc.name, pc.id, pc.introduced, pc.discontinued, pc.company_id, comp.name "
			  									 			+ "FROM computer AS pc "
			  									 			+ "LEFT JOIN company AS comp ON "
			  									 			+ "comp.id = pc.company_id "
			  									 			+ "WHERE pc.id = :pcId";
	private static final String ADD_NEW_COMPUTER_DB = "INSERT INTO computer (name,introduced,discontinued,company_id) VALUES ( :name, :introduced, :discontinued, :company_id)";
	private static final String UPDATE_COMPUTER_DB = "UPDATE computer SET name = :name, introduced = :introduced, discontinued = :discontinued, company_id = :company_id WHERE id = :pc_id";
	private static final String DELETE_COMPUTER_DB = "DELETE FROM computer WHERE id = :pc_id";
	private static final String COUNT_ALL_COMPUTERS_QUERY = "SELECT COUNT(id) AS rowcount FROM computer";
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	@Autowired
	private ComputerRawMapper pcRawMapper;
	
	
	private ComputerDAO() {} //private constructor, singleton
	
	/**
	 * Create an List of Computers corresponding to all the registered Computer in the DB
	 * @return List with all the computers in computer-database
	 */
	public List<Computer> getAllComputersRequest() {
		try {
			return jdbcTemplate.query(GET_ALL_COMPUTER_DETAILS_QUERY, pcRawMapper);
		} catch (DataAccessException dataE) {
			dataE.printStackTrace();
			return Collections.emptyList();
		}
	}
	
	/**
	 * search for a specifique computer in db, return it as optional if exist
	 * @param id
	 * @return Optional<Computer>
	 */
	public Optional<Computer> getOneComputers(long id) {
		try {
			MapSqlParameterSource paramMap = new MapSqlParameterSource().addValue("pcId", id);
			List<Computer> res = jdbcTemplate.query(GET_COMPUTER_DETAILS_QUERY, paramMap, pcRawMapper);
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
	 * add a new PC in the db following the given informations
	 * @param the pc to add in the db
	 * @return the id associated to the new PC
	 */
	public int insertNewComputer(Computer pc) {
		try {
			MapSqlParameterSource paramMap = new MapSqlParameterSource()
												.addValue("name", pc.getName())
												.addValue("introduced", pc.getIntroduced())
												.addValue("discontinued", pc.getDiscontinued());
			Company comp = pc.getcompany();
			if (comp == null) {
				paramMap.addValue("company_id", null);
			}
			else {
				paramMap.addValue("company_id", pc.getcompany().getCompId());
			}
			return jdbcTemplate.update(ADD_NEW_COMPUTER_DB, paramMap);
		} catch (DataAccessException sqle) {
			sqle.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * update a PC with pc.id= computer.id in the db
	 * @param pc
	 * @return 1 if the update were done correctly, 0 otherwise
	 */
	public int updateComputer(Computer pc) {
		try {
			MapSqlParameterSource paramMap = new MapSqlParameterSource()
												.addValue("name", pc.getName())
												.addValue("introduced", pc.getIntroduced())
												.addValue("discontinued", pc.getDiscontinued())
												.addValue("pc_id", pc.getPcId());
			Company comp = pc.getcompany();
			if (comp == null) {
				paramMap.addValue("company_id", null);
			}
			else {
				paramMap.addValue("company_id", pc.getcompany().getCompId());
			}
			return jdbcTemplate.update(UPDATE_COMPUTER_DB, paramMap);
		} catch (DataAccessException sqle) {
			sqle.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * delete a specified computer in the db
	 * @param id
	 * @return number of row deleted (should be 1 or 0)
	 */
	public int deleteComputer (long id) {
		try {
			MapSqlParameterSource paramMap = new MapSqlParameterSource().addValue("pc_id", id);
			return jdbcTemplate.update(DELETE_COMPUTER_DB, paramMap);
		} catch (DataAccessException sqle) {
			sqle.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * Get the number of different PC in computer
	 * @return the number of PC
	 */
	public int countAllComputer() {
		try {
			return jdbcTemplate.queryForObject(COUNT_ALL_COMPUTERS_QUERY, new MapSqlParameterSource(), Integer.class);
		} catch (DataAccessException sqle) {
			sqle.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * Create an List of Computers corresponding to the registered Computer in the DB in the page range
	 * @param the page
	 * @return List with the computers in computer-database
	 */
	public List<Computer> getPageComputersRequest(Pagination page, OrderByPossibilities order) {
		try {
			MapSqlParameterSource paramMap = new MapSqlParameterSource()
											.addValue("start", page.getActualPageNb() * page.getPageSize())
											.addValue("qty", page.getPageSize());
			return jdbcTemplate.query(GET_ALL_COMPUTER_DETAILS_QUERY + order.getOrderBy(), paramMap, pcRawMapper);
		} catch(DataAccessException sqle) {
			sqle.printStackTrace();
			return Collections.emptyList();
		}
	}
	
	/**
	 * Create a List of all the computer with a name or company name containing the given name
	 * @param name
	 * @return
	 */
	public List<Computer> searchComputersByName(String name){
		try {
			MapSqlParameterSource paramMap = new MapSqlParameterSource()
											.addValue("name", "%" + name + "%");
			return jdbcTemplate.query(GET_COMPUTER_BY_NAME_QUERY, paramMap, pcRawMapper);
		} catch(DataAccessException sqle) {
			sqle.printStackTrace();
			return Collections.emptyList();
		}
	}

	/**
	 * Create a list of all the computer with with the given company name
	 * @param name
	 * @return
	 */
	public List<Computer> getComputerByCompanyId(long id){
		try {
			MapSqlParameterSource paramMap = new MapSqlParameterSource()
											.addValue("company_id", id);
			return jdbcTemplate.query(GET_COMPUTER_BY_COMPANY_ID_QUERY, paramMap, pcRawMapper);
		} catch(DataAccessException sqle) {
			sqle.printStackTrace();
			return Collections.emptyList();
		}
	}
	
}
