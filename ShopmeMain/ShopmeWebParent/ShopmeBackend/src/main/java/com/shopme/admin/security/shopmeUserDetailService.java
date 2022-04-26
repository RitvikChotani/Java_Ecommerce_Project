package com.shopme.admin.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.shopme.admin.exception.UserNotFoundException;
import com.shopme.admin.repository.UserRepository;
import com.shopme.common.entity.User;

public class shopmeUserDetailService implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		User user = userRepo.getUserByEmail(email);
		
		if(user != null) {
			return new shopmeUserDetails(user);
		}
		
		throw new UsernameNotFoundException(email);
	}

}
