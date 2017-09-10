package com.fontdb.demo.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.fontdb.demo.model.Role;
import com.fontdb.demo.model.User;

public interface UserRepository extends PagingAndSortingRepository<User, Integer> {
    User findByEmail(String email);
	public List<User> findByNameContainingAndEmailContainingAndRoleIn(String name, String email, List<Role> roles);
}
