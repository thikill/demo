package com.fontdb.demo;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import com.fontdb.demo.model.Role;
import com.fontdb.demo.model.User;
import com.fontdb.demo.repository.UserRepository;


@RunWith(SpringRunner.class)
@SpringBootTest
public class FontdbDemoApplicationTests {
	@Autowired
	private UserRepository userRepository;
	@Test
	public void searchUser() {
		Role role = new Role();
		role.setId(1);
		role.setName("Admin");
		
		//List<User> users = userRepository.findByNameContainingAndEmailContainingAndRoleEquals("%%", "%%", role);
		//assertEquals(1, users.size());
	}
	
	
	@Test
	public void searchPageUser() {
		int rows=2;
		
		Pageable pageReq = new PageRequest(1, rows);
		assertEquals(rows, userRepository.findAll(pageReq).getContent().size());

	}	
	
	

	@Test
	public void contextLoads() {
	}

}
