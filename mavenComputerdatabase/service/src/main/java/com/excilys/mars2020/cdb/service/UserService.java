package com.excilys.mars2020.cdb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.mars2020.cdb.dto.UserDTO;
import com.excilys.mars2020.cdb.mapper.UserMapper;
import com.excilys.mars2020.cdb.persistence.UserDAO;

@Service
public class UserService {

	@Autowired
	UserDAO userdao;
	
	@Autowired
	UserMapper mapper;
	
	public boolean checkConnectionData (UserDTO user) {
		return userdao.checkConnectionData(user.getUsername(), user.getPassword());
	}
	
	public UserDTO getUser(UserDTO user) {
		return mapper.userToUserDTO(userdao.getUser(mapper.userDTOToUser(user)));
	}
	
	public void addUser(UserDTO user) {
		userdao.addUser(mapper.userDTOToUser(user));
	}
}
