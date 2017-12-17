package com.example.service;

import com.example.model.User;

public interface UserService {

	public static final int ACTIVATED_USER = 1;
	public static final int NOT_ACTIVATED_USER = 0;

	public User findUserByEmail(String email);
	public void saveUser(User user);
	public User updateUser(User user);
	public String extractUsername(String authHeader);
	public String randomWord();
	public User activateUser(User user);
	public User deleteUser(String username);
	public User resetPass(User user);
}
