package com.adam.service;

import com.adam.exception.BadRequestException;
import com.adam.model.User;
import com.adam.model.UserDto;
import com.adam.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username).orElse(null);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				new ArrayList<>());
	}
	
	public User register(UserDto user) throws Exception {
		if (userRepository.findByUsername(user.getUsername()).isPresent()){
			throw new BadRequestException(  "username taken" );
		}
		if (userRepository.findByEmail(user.getEmail()).isPresent()){
			throw new BadRequestException(  "email taken" );
		}

		User newUser = new User();
		newUser.setName(user.getName());
		newUser.setUsername(user.getUsername());
		newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		newUser.setEmail(user.getEmail());
		return userRepository.save(newUser);
	}
}
