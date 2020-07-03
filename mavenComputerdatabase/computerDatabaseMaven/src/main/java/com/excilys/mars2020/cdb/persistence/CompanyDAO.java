package com.excilys.mars2020.cdb.persistence;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.excilys.mars2020.cdb.model.Company;
import com.excilys.mars2020.cdb.model.Company_;
import com.excilys.mars2020.cdb.model.Pagination;

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
		return companyList.getResultList();
	}
	
	/**
	 * get the company with id = id in the BD and retrieve it as Optional
	 * @param id
	 * @return Optional with the company, empty if doesn't exist
	 */
	public Optional<Company> getOneCompanyRequest(long id){
		
		EntityManager em = entityManagerFactory.createEntityManager();
		criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Company> cq = criteriaBuilder.createQuery(Company.class);
		
		Root<Company> root = cq.from(Company.class);
		Predicate byId = criteriaBuilder.equal(root.get(Company_.compId), id);
		cq.select(root).where(byId);
		
		TypedQuery<Company> companyList = em.createQuery(cq);
		Company comp = companyList.getSingleResult();
		if(comp != null) {
			return Optional.of(comp);
		}
		return Optional.empty();
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
		return companyList.getResultList();
	}
	
	@Transactional
	public int deleteCompany(long id) {
		
		EntityManager em = entityManagerFactory.createEntityManager();
		criteriaBuilder = em.getCriteriaBuilder();
		CriteriaDelete<Company> cd = criteriaBuilder.createCriteriaDelete(Company.class);
		
		Root<Company> root = cd.from(Company.class);
		Predicate computerId = criteriaBuilder.equal(root.get(Company_.compId), id);
		cd.where(computerId);
		
		em.getTransaction().begin();
		int res = em.createQuery(cd).executeUpdate();
		em.getTransaction().commit();
		return res;
	}
}
