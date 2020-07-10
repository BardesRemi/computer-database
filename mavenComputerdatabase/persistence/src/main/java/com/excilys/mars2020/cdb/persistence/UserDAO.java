package com.excilys.mars2020.cdb.persistence;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.excilys.mars2020.cdb.model.User;
import com.excilys.mars2020.cdb.model.User_;

@Repository
public class UserDAO {

	private final static Logger LOGGER = LoggerFactory.getLogger(UserDAO.class);
	
	private CriteriaBuilder criteriaBuilder;
	
	@PersistenceContext
	private EntityManager em;
	
	/**
	 * 
	 * @param username
	 * @param password
	 * @return the id corresponding to the user
	 */
	public Optional<Long> getConnection (String username, String password) {
		criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<User> cq = criteriaBuilder.createQuery(User.class);
		
		Root<User> root = cq.from(User.class);
		Predicate byUsername = criteriaBuilder.equal(root.get(User_.username), username);
		Predicate byPassword = criteriaBuilder.equal(root.get(User_.password), password);
		Predicate and = criteriaBuilder.and(byUsername, byPassword);
		cq.select(root).where(and);
		
		TypedQuery<User> userList = em.createQuery(cq);
		try {
			User user = userList.getSingleResult();
			LOGGER.info("User with username = {} found", username);
			return Optional.of(user.getId());
		} catch (NoResultException noResExc) {
			LOGGER.info("No User with username {} and Password {} in the db", username, password);
			return Optional.empty();
		} catch (NonUniqueResultException multResExc) {
			LOGGER.info("Multiple User with username {} and Password {} in the db", username, password);
			return Optional.empty();
		} catch (Exception e) {
			LOGGER.info("Something went wrong when User with username {} and Password {} was searched in the db", username, password);
			return Optional.empty();
		}
		
	}
	
	/**
	 * 
	 * @param id
	 * @return The User corresponding to the one with the given id in the database
	 */
	public Optional<User> getUser(long id) {
		criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<User> cq = criteriaBuilder.createQuery(User.class);
		
		Root<User> root = cq.from(User.class);
		Predicate byId = criteriaBuilder.equal(root.get(User_.id), id);
		cq.select(root).where(byId);
		
		TypedQuery<User> userList = em.createQuery(cq);
		User user = userList.getSingleResult();
		if(user != null) {
			LOGGER.info("User with id = {} found", id);
			return Optional.of(user);
		}
		LOGGER.info("No User with id {} in the db", id);
		return Optional.empty();
	}
	
	/**
	 * Add a User with the given caracteristics to the db
	 * @param user
	 * @return user correctly added or not
	 */
	@Transactional
	public boolean addUser(User user) {
		
		try {
			em.persist(user);
			LOGGER.info("User added on the db : {}", user.toString());
			return true;
		} catch (Exception e) {
			LOGGER.info("Error when inserting a new user in the db for : {}", user.toString());
			return false;
		}
	}
	
}
