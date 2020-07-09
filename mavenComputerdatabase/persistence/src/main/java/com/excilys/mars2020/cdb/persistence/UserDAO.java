package com.excilys.mars2020.cdb.persistence;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.excilys.mars2020.cdb.model.User;

@Repository
public class UserDAO {

	private final static Logger LOGGER = LoggerFactory.getLogger(UserDAO.class);
	
	
	public boolean checkConnectionData (String username, String password) {
		return true;
	}
	
	public User getUser(User user) {
		return user;
	}
	
	@Transactional
	public void addUser(User user) {
		
	}
	
}
