package com.shopme.admin.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.shopme.admin.exception.UserNotFoundException;
import com.shopme.admin.repository.RoleRepository;
import com.shopme.admin.repository.UserRepository;
import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@Service
@Transactional
public class UserService {
	
	public static final int USER_PER_PAGE = 4;
	
	@Autowired
	private UserRepository userRepo;

	@Autowired
	private RoleRepository roleRepo;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	public List<User> listAll() {
		return (List<User>) userRepo.findAll(Sort.by("firstName").ascending());
	}
	
	public List<Role> listAllRole() {
		return (List<Role>) roleRepo.findAll();
	}
	
	public User getUserByEmail(String email) {
		return userRepo.getUserByEmail(email);
	}

	public Page<User> listAllByPage(int pagenum, String sortField, String sortDir, String keyword) {
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		Pageable pageable = PageRequest.of(pagenum - 1, USER_PER_PAGE, sort);
		
		if(keyword != null) {
			return userRepo.findAll(keyword, pageable);
			
		}
		return userRepo.findAll(pageable);
	}
	
	public User saveUser(User user) {
		boolean isUpdating = (user.getId() != null);
		if(isUpdating) {
			User existingUser = userRepo.findById(user.getId()).get();
			if(user.getPassword().isEmpty()) {
				user.setPassword(existingUser.getPassword());
			}
			else {
				encodePass(user);
			}
		}
		return userRepo.save(user);	
	}
	
	public User updateAccount(User userInForm) {
		User userInDb = userRepo.findById(userInForm.getId()).get();
		
		if(!userInForm.getPassword().isEmpty()) {
			userInDb.setPassword((userInForm.getPassword()));
			encodePass(userInDb);
		}
		
		if(userInForm.getPhotos() != null) {
			userInDb.setPhotos(userInForm.getPhotos());
		}
		
		userInDb.setFirstName(userInForm.getFirstName());
		userInDb.setLastName(userInForm.getLastName());
		
		return userRepo.save(userInDb);
	}
	
	public void encodePass(User user) {
		user.setPassword(encoder.encode(user.getPassword()));
	}
	
	public boolean isEmailUnique(String email, Integer id) {
		User userByEmail = userRepo.getUserByEmail(email);
		if(userByEmail == null) {
			return true;
		}
		
		boolean isNew = (id == null);
		
		if(isNew) {
			if (userByEmail != null) {
				return false;
			}
		} else {
			if(userByEmail.getId() != id)
				return false;	
		}
		return true;
	}

	public User getById(Integer id) throws UserNotFoundException {
		try {
			return userRepo.findById(id).get();
		} catch (Exception e) {
			throw new UserNotFoundException("Could not find user with ID " + id);
		}
	}
	
	public void deleteUser(Integer id) throws UserNotFoundException {
		Long count = userRepo.countById(id);
		
		if(count == null || count == 0) {
			throw new UserNotFoundException("Could not find user with ID " + id);
		}
		
		userRepo.deleteById(id);
	}
	
	public void enableUser(Integer id, boolean enabled) {
		userRepo.updateEnableUserStatus(id, enabled);
	}
}
