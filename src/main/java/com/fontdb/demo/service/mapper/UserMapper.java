package com.fontdb.demo.service.mapper;

import org.springframework.stereotype.Service;

import com.fontdb.demo.model.User;
import com.fontdb.demo.web.dto.UserDto;

@Service
public class UserMapper extends BaseMapper<User, UserDto> {
	@Override
	public UserDto modelToDto(User model) {
		return new UserDto(model);
	}

}