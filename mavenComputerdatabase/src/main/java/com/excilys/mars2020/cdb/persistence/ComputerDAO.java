package com.excilys.mars2020.cdb.persistence;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

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
	
	
	
	private CriteriaBuilder criteriaBuilder;
	
	@PersistenceContext
	private EntityManager em;
	
	/**
	 * Create an List of Computers corresponding to all the registered Computer in the DB
	 * @return List with all the computers in computer-database
	 */
	public List<Computer> getAllComputersRequest() {

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

		criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Computer> cq = criteriaBuilder.createQuery(Computer.class);
		
		Root<Computer> root = cq.from(Computer.class);
		Predicate byId = criteriaBuilder.equal(root.get(Computer_.pcId), id);
		cq.select(root).where(byId);
		
		TypedQuery<Computer> computerList = em.createQuery(cq);
		Computer pc = computerList.getSingleResult();
		if(pc != null) {
			return Optional.of(pc);
		}
		return Optional.empty();
	}
	
	/**
	 * add a new PC in the db following the given informations
	 * @param the pc to add in the db
	 * @return how the insert goes (with an int)
	 */
	@Transactional
	public int insertNewComputer(Computer pc) {

		try {
			em.persist(pc);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return  0;
		}
	}
	
	/**
	 * update a PC with pc.id= computer.id in the db
	 * @param pc
	 * @return 1 if the update were done correctly, 0 otherwise
	 */
	@Transactional
	public int updateComputer(Computer pc) {

		criteriaBuilder = em.getCriteriaBuilder();
		
		CriteriaUpdate<Computer> update = criteriaBuilder.createCriteriaUpdate(Computer.class);
		Root<Computer> root = update.from(Computer.class);
		
		update.set(Computer_.name, pc.getName());
		update.set(Computer_.introduced, pc.getIntroduced());
		update.set(Computer_.discontinued, pc.getDiscontinued());
		update.set(Computer_.company, pc.getcompany());
		update.where(criteriaBuilder.equal(root.get(Computer_.pcId), pc.getPcId()));
		
		int res = em.createQuery(update).executeUpdate();
		return res;
	}
	
	/**
	 * delete a specified computer in the db
	 * @param id
	 * @return number of row deleted (should be 1 or 0)
	 */
	@Transactional
	public int deleteComputer (long id) {
		
		criteriaBuilder = em.getCriteriaBuilder();
		CriteriaDelete<Computer> cd = criteriaBuilder.createCriteriaDelete(Computer.class);
		
		Root<Computer> root = cd.from(Computer.class);
		Predicate computerId = criteriaBuilder.equal(root.get(Computer_.pcId), id);
		cd.where(computerId);
		
		int res = em.createQuery(cd).executeUpdate();
		return res;
	}
	
	/**
	 * Get the number of different PC in computer
	 * @return the number of PC
	 */
	public long countAllComputer() {

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

		criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Computer> cq = criteriaBuilder.createQuery(Computer.class);
		
		Root<Computer> root = cq.from(Computer.class);
		cq.select(root);
		
		Join<Company, Computer> companyGroup = root.join("company", JoinType.LEFT);
		String searchParam = "%" + name.toLowerCase() + "%";
		Predicate byComputerName = criteriaBuilder.like(root.get("name"), searchParam);
		Predicate byCompanyName = criteriaBuilder.like(companyGroup.get("name"), searchParam);
		Predicate orSearch = criteriaBuilder.or(byCompanyName, byComputerName);
		cq.where(orSearch);
		
		TypedQuery<Computer> computerList = em.createQuery(cq);
		return computerList.getResultList();
	}

	/**
	 * Create a list of all the computer with with the given company name
	 * @param name
	 * @return
	 */
	public List<Computer> getComputerByCompanyId(long id){
		
		criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Computer> cq = criteriaBuilder.createQuery(Computer.class);
		
		Root<Computer> root = cq.from(Computer.class);
		cq.select(root);
		
		Join<Company, Computer> companyGroup = root.join("company", JoinType.LEFT);
		
		Predicate byCompId = criteriaBuilder.equal(companyGroup.get("compId"), id);
		cq.where(byCompId);
		
		TypedQuery<Computer> computerList = em.createQuery(cq);
		return computerList.getResultList();
	}
	
}
