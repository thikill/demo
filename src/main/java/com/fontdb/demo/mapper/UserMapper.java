package com.fontdb.demo.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fontdb.demo.dto.UserDto;
import com.fontdb.demo.model.User;
@Service
public class UserMapper {
	public UserDto userToUserDto(User user) {
		return new UserDto(user);
	}

	public List<UserDto> usersToUserDtos(List<User> users) {
		return users.stream().map(this::userToUserDto).collect(Collectors.toList());
	}

}