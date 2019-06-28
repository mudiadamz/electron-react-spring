package com.adam.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "username", unique = true)
	@Size(max = 50)
	private String username;

	@Column(name = "password")
	@JsonIgnore
	private String password;

	@Column(name = "name")
	private String name;

	@Column(name = "email", unique = true)
	@Size(max = 50)
	@Email
	private String email;

}
