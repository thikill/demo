package com.fontdb.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fontdb.demo.model.Role;
import com.fontdb.demo.repository.RoleRepository;

@Service
public class RoleService {
	@Autowired
	private RoleRepository roleRepository;
	public Iterable<Role> findAll() {
		return roleRepository.findAll();
	}
	
	
}

