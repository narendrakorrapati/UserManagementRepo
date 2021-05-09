package com.narendra.bss.service;

import java.util.List;
import java.util.Locale;

import javax.mail.MessagingException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.narendra.bss.dto.UserDTO;
import com.narendra.bss.entity.User;
import com.narendra.bss.exception.UserNotFoundException;



public interface UserService {
	
	Integer MAX_LOGIN_ATTEMPTS_ALLOWED = 3;
	Long UNLOCK_TIME_LIMIT = 3 * 60 *1000L;
	
	User findById(int id);
	
	void saveUser(User user);
	
	void updateUser(UserDTO user);
	
	void deleteByEmail(String email);
	
	void deleteByIdIn(List<Integer> ids);
	
	void deleteById(Integer id);

	Page<User> findAllUsers(int page, int size, String sort, String order, String search); 
	
	Page<User> findByFirstNameLike(String firstName, Pageable pageable); 
	
	int updatefailedLoginAttempts(User user);

	void lockUser(User user);

	boolean unlockUser(User user);

	int resetFailedLoginAttempts(Integer id);
	
	long countByEmail(String email);
	
	void saveUser(UserDTO userModel,  String siteLink, Locale locale) throws MessagingException;

	void verifyOldPassword(String oldPassword, String password) throws Exception;

	void saveUser(User user, String newPassword);
	
	User findByEmail(String email) throws UserNotFoundException;

	void sendResetPassLink(User user, String resetLink, Locale locale) throws MessagingException;

	User findByForgotPasswordToken(String token) throws UserNotFoundException;

	Long countByEmailIdNotEqual(String email, Integer id);
}