package com.fontdb.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fontdb.demo.model.Role;
import com.fontdb.demo.model.User;
import com.fontdb.demo.repository.RoleRepository;
import com.fontdb.demo.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;	
	
	
	  public Page<User> page(Pageable page)
	  {

		  return userRepository.findAll(page);
	  }
	  
	  

	public Iterable<User> findAll() {
		return userRepository.findAll();
	}

	public void save(User model) {
		userRepository.save(model);
	}

	public User findOne(Integer id) {
		return userRepository.findOne(id);
	}

	public void delete(Integer id) {
		userRepository.delete(id);
	}

	public List<User> searchUser(User user) {
		List<Role> roles = new ArrayList<>();
		if (user.getRole() != null) {
			roles.add(user.getRole());
		} else {
			roleRepository.findAll().forEach(roles::add);
		}
		return userRepository.findByNameContainingAndEmailContainingAndRoleIn(user.getName(), user.getEmail(), roles);
	}
}
