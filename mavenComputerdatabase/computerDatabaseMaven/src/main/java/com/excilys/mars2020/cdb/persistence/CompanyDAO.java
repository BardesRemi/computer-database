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
import javax.persistence.criteria.Root;

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
	
	@PersistenceUnit
	private EntityManagerFactory entityManagerFactory;
	
	private CriteriaBuilder criteriaBuilder;
	
	@Autowired
	private static ComputerDAO pcdao;
	
	private static final String GET_ONE_COMPANY_QUERY = "SELECT id, name FROM company WHERE id = :compId";
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	@Autowired
	private CompanyRawMapper compRawMapper;
	
	
	
	private CompanyDAO() {}
	
	/**
	 * fetch all Companies from db and return them as an ArrayList
	 * @return ArrayList with all the companies
	 */
	public List<Company> getAllCompaniesRequest(){
		
		EntityManager em = entityManagerFactory.createEntityManager();
		criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Company> cq = criteriaBuilder.createQuery(Company.class);
		
		Root<Company> root = cq.from(Company.class);
		cq.select(root);
		
		TypedQuery<Company> companyList = em.createQuery(cq);
		em.close();
		return companyList.getResultList();
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
	public long countAllCompanies() {
		EntityManager em = entityManagerFactory.createEntityManager();
		criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = criteriaBuilder.createQuery(Long.class);
		cq.select(criteriaBuilder.count(cq.from(Company.class)));
		return em.createQuery(cq).getSingleResult();
	}
	
	/**
	 * Create an ArrayList of Companies corresponding to the registered Companies in the DB in the page range
	 * @return ArrayList with the Companies in computer-database
	 */
	public List<Company> getPageCompaniesRequest(Pagination page) {
		
		EntityManager em = entityManagerFactory.createEntityManager();
		criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Company> cq = criteriaBuilder.createQuery(Company.class);
		
		Root<Company> root = cq.from(Company.class);
		cq.select(root);
		
		TypedQuery<Company> companyList = em.createQuery(cq)
											.setFirstResult(page.getActualPageNb() * page.getPageSize())
											.setMaxResults(page.getPageSize());
		em.close();
		return companyList.getResultList();
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
