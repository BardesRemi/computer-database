package com.excilys.mars2020.cdb.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.mars2020.cdb.dto.UserDTO;
import com.excilys.mars2020.cdb.model.User;

@Service
public class CustomUserDetailService implements UserDetailsService{
	
	@Autowired
	UserService userService;
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		throw new UsernameNotFoundException("no password given => no username searched");
	}
	
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username, String password) throws UsernameNotFoundException{
		
		if (username.trim().isEmpty()) {
			throw new UsernameNotFoundException("username is empty");
		}
		
		long userId = userService.checkConnectionData(new UserDTO.Builder(username, password).build());
		//shouldn't crash since user availability is checked beforehand
		Optional<User> userOpt = userService.getUser(userId);
		
		if(userOpt.isEmpty()) {
			throw new UsernameNotFoundException("User " + username + " not found");
		}
		
		User user = userOpt.get(); 
		
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getGrantedAuthorities(user));
	}

	private List<GrantedAuthority> getGrantedAuthorities(User user) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		String role = user.getRole();
		authorities.add(new SimpleGrantedAuthority(role));
		return authorities;
	}
	
	

}
