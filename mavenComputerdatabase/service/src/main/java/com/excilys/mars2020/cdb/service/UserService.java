package com.excilys.mars2020.cdb.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.mars2020.cdb.dto.UserDTO;
import com.excilys.mars2020.cdb.mapper.UserMapper;
import com.excilys.mars2020.cdb.model.User;
import com.excilys.mars2020.cdb.persistence.UserDAO;

@Service
public class UserService {

	@Autowired
	UserDAO userdao;
	
	@Autowired
	UserMapper mapper;
	
	public long checkConnectionData (UserDTO user) {
		Optional<Long> res = userdao.getConnection(user.getUsername(), user.getPassword());
		return res.isEmpty() ? -1 : res.get();
	}
	
	public Optional<User> getUser(UserDTO user) {
		Optional<User> res = userdao.getUser(Long.valueOf(user.getId()));
		return res.isEmpty() ? Optional.empty() : res;
	}
	
	public Optional<User> getUser(long id) {
		Optional<User> res = userdao.getUser(id);
		return res.isEmpty() ? Optional.empty() : res;
	}
	
	public void addUser(UserDTO user) {
		userdao.addUser(mapper.userDTOToUser(user));
	}
}
