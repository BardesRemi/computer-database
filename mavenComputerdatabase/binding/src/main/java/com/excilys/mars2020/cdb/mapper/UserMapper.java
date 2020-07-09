package com.excilys.mars2020.cdb.mapper;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.mars2020.cdb.dto.UserDTO;
import com.excilys.mars2020.cdb.model.User;

@Component
public class UserMapper {

	@Autowired
	Mapper mapper;
	
	public UserDTO userToUserDTO (User user) {
		return new UserDTO.Builder(user.getUsername(), user.getPassword())
						  .id(String.valueOf(user.getId()))
						  .role(user.getRole()).enabled(String.valueOf(user.isEnabled()))
						  .build();
	}
	
	public User userDTOToUser (UserDTO user) {
		Optional<Long> id = mapper.stringToLong(user.getId());
		return new User.Builder(user.getUsername(), user.getPassword())
									.id(id.orElseGet(null))
									.enabled(Boolean.valueOf(user.getEnabled()))
									.role(user.getRole())
									.build();
	}
	
	
}
