package com.excilys.mars2020.cdb.persistence;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.mars2020.cdb.model.Company;
import com.excilys.mars2020.cdb.model.Computer;
import com.excilys.mars2020.cdb.model.Computer_;
import com.excilys.mars2020.cdb.model.Pagination;

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
	
	
	@PersistenceUnit
	private EntityManagerFactory entityManagerFactory;
	
	private CriteriaBuilder criteriaBuilder;
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
		
		EntityManager em = entityManagerFactory.createEntityManager();
		criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Computer> cq = criteriaBuilder.createQuery(Computer.class);
		
		Root<Computer> root = cq.from(Computer.class);
		cq.select(root);
		
		TypedQuery<Computer> computerList = em.createQuery(cq);
		return computerList.getResultList();
	}
	
	/**
	 * search for a specifique computer in db, return it as optional if exist
	 * @param id
	 * @return Optional<Computer>
	 */
	public Optional<Computer> getOneComputers(long id) {
		EntityManager em = entityManagerFactory.createEntityManager();
		criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Computer> cq = criteriaBuilder.createQuery(Computer.class);
		
		Root<Computer> root = cq.from(Computer.class);
		Predicate byId = criteriaBuilder.equal(root.get("id"), id);
		cq.select(root).where(byId);
		
		TypedQuery<Computer> computerList = em.createQuery(cq);
		Computer pc = computerList.getSingleResult();
		em.close();
		if(pc != null) {
			return Optional.of(pc);
		}
		return Optional.empty();
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
	public long countAllComputer() {
		EntityManager em = entityManagerFactory.createEntityManager();
		criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = criteriaBuilder.createQuery(Long.class);
		cq.select(criteriaBuilder.count(cq.from(Computer.class)));
		return em.createQuery(cq).getSingleResult();
	}
	
	/**
	 * Create an List of Computers corresponding to the registered Computer in the DB in the page range
	 * @param the page
	 * @return List with the computers in computer-database
	 */
	public List<Computer> getPageComputersRequest(Pagination page, OrderByPossibilities order) {
		EntityManager em = entityManagerFactory.createEntityManager();
		criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Computer> cq = criteriaBuilder.createQuery(Computer.class);
		
		Root<Computer> root = cq.from(Computer.class);
		switch(order) {
		case ID_UP :
			cq.orderBy(criteriaBuilder.asc(root.get(Computer_.pcId)));
			break;
		case PC_UP :
			cq.orderBy(criteriaBuilder.asc(root.get(Computer_.name)));
			break;
		case COMPANY_UP :
			cq.orderBy(criteriaBuilder.asc(root.get(Computer_.company.getName())));
			break;
		default :
			//shouldn't happen
		}
		cq.select(root);
		TypedQuery<Computer> companyList = em.createQuery(cq)
											.setFirstResult(page.getActualPageNb() * page.getPageSize())
											.setMaxResults(page.getPageSize());
		return companyList.getResultList();
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
