package com.example.service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashSet;

import org.apache.commons.lang3.RandomStringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.model.Role;
import com.example.model.User;
import com.example.repository.RoleRepository;
import com.example.repository.UserRepository;

@Service("userService")
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	@Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
	private MailService mailService;
	
	@Override
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public void saveUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(NOT_ACTIVATED_USER);
        user.setKeyword(randomWord());
        user.setRegdate(DateTime.now());
        Role userRole = roleRepository.findByRole("ADMIN");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		userRepository.save(user);

		mailService.sendActivationMail(user);
	}

	@Override
	public User updateUser(User user) {
		User newUser = findUserByEmail(user.getEmail());
		if(newUser != null) {
			newUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			userRepository.save(newUser);
		}

		return newUser;
	}

	@Override
	public User resetPass(User user){
		User newUser = findUserByEmail(user.getEmail());
		if(newUser != null) {
			String passwd = randomWord();
			newUser.setPassword(bCryptPasswordEncoder.encode(passwd));
			userRepository.save(newUser);
			mailService.sendNewPasswordMail(newUser, passwd);
		}

		return newUser;
	}

	@Override
	public String extractUsername(String authHeader) {
		byte[] decodedBytes = Base64.getDecoder().decode(authHeader.split(" ")[1]);
		String decodedUsername = new String(decodedBytes).split(":")[0];

		return new String(decodedUsername);
	}

	@Override
	public User activateUser(User userToActivate) {
		User user = userRepository.findByEmail(userToActivate.getEmail());
		if(    user != null
		    && user.getKeyword().equals(userToActivate.getKeyword()))
		{
			user.setActive(ACTIVATED_USER);
			userRepository.save(user);
		}

		return user;
	}

	@Override
	public User deleteUser(String username) {
		User user = userRepository.findByEmail(username);
		if(user != null) {
			user.setActive(NOT_ACTIVATED_USER);
			userRepository.save(user);
		}

		return user;
	}

//	@Override
	public String randomWord() {
		return RandomStringUtils.randomAlphabetic(16);
	}

}
