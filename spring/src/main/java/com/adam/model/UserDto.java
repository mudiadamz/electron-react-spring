package com.adam.model;

import lombok.Data;

@Data
public class UserDto {
	private String username;
	private String password;
    private String name;
    private String email;

	public UserDto(String username, String password, String name, String email) {
		this.username = username;
		this.password = password;
		this.name = name;
		this.email = email;
	}
}
