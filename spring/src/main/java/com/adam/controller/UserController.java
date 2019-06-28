package com.adam.controller;

import com.adam.model.User;
import com.adam.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import com.adam.service.UserService;


import com.adam.config.JwtTokenUtil;
import com.adam.model.JwtRequest;
import com.adam.model.JwtResponse;
import com.adam.model.UserDto;

import java.security.Principal;

@RestController
@CrossOrigin
@RequestMapping("user")
public class UserController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserService userService;
	@Autowired
    private UserRepository userRepository;

    @PostMapping( "/auth" )
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = userService
				.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new JwtResponse(token));
	}

	@PostMapping( "/register" )
	public ResponseEntity<?> saveUser(@RequestBody UserDto user) throws Exception {

		return ResponseEntity.ok(userService.register(user));
	}

	@GetMapping("/me")
    public User user(Authentication authentication){
        return userRepository.findByUsername( authentication.getName() ).orElse(null);
    }

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}
