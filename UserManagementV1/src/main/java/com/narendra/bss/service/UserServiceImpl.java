package com.narendra.bss.service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.narendra.bss.dao.UserDao;
import com.narendra.bss.dto.UserDTO;
import com.narendra.bss.entity.User;
import com.narendra.bss.entity.UserRole;
import com.narendra.bss.exception.UserNotFoundException;


@Service("userService")
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao dao;

	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PasswordGeneratorService passwordGeneratorService;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private EmailTemplateService emailTemplateService;
	
	public User findById(int id) {
		return dao.findById(id).get();
	}

	@org.springframework.transaction.annotation.Transactional
	public void saveUser(UserDTO userModel, String siteLink, Locale locale) throws MessagingException {
		User user = modelMapper.map(userModel, User.class);
		String password = passwordGeneratorService.generatePassword();
		
		Set<UserRole> userRoles = new HashSet<UserRole>(userRoleService.findByIdIn(userModel.getUserRoles()));
		user.setUserRoles(userRoles);
		user.setPassword(password);
		
		saveUser(user);
		
		userModel.setId(user.getId());
		
		sendOTPToUser(user,password, siteLink, locale);
	}

	private void sendOTPToUser(User user,String password, String siteLink, Locale locale) throws MessagingException {
		
		System.out.println(password + "--->" + passwordEncoder.encode(password));
		
		String subject = messageSource.getMessage("user.otp.email.subject", null, locale);
		
		String body = emailTemplateService.getEmailTemplate("email-templates/new-user-otp.html");
		
		emailService.sendEmail(user.getEmail(), subject, String.format(body, new Object[] {user.getFirstName(), user.getLastName(), user.getEmail(), password, siteLink}));
	}

	public void saveUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		dao.save(user);
	}
	
	@Override
	public void saveUser(User user, String newPassword) {
		user.setPassword(passwordEncoder.encode(newPassword));
		user.setFirstTimeLogin(false);
		user.setLastPasswordTime(new Date());
		dao.save(user);
		
	}
	
	@Transactional
	public void updateUser(UserDTO user) {
		User entity = dao.findById(user.getId()).get();
		
		if(entity!=null) {
			
			entity.setFirstName(user.getFirstName());
			entity.setLastName(user.getLastName());
			/* entity.setEmail(user.getEmail()); */
			
			entity.setUserRoles(new HashSet<>(userRoleService.findByIdIn(user.getUserRoles())));
			
		}
	}
	
	public void deleteByEmail(String email) {
		dao.deleteByEmail(email);
	}

	public Page<User> findAllUsers(int page, int size, String sort, String order, String search) {
		
		Pageable pageable = null;
		
		if(StringUtils.hasLength(sort)) {
			
			pageable = PageRequest.of(page, size, Sort.by(("asc".equalsIgnoreCase(order) ? Sort.Direction.ASC :Sort.Direction.DESC) , sort));
		} else {
			pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "firstName"));
		}
		
		if(StringUtils.hasLength(search)) {
			return dao.findByFirstNameLikeIgnoreCase(search, pageable);
		} else {
			return dao.findAllCustom(pageable);
		}
		
	}

	@Transactional
	@Override
	public int updatefailedLoginAttempts(User user) {
		return dao.updatefailedLoginAttempts(user.getFailedLoginAttempts() +1, user.getId());
	}

	@Override
	public void lockUser(User user) {
		user.setAccountNonLocked(false);
		user.setLockTime(new Date());
		dao.save(user);
	}

	@Override
	public boolean unlockUser(User user) {
		
		long lockTimeinMills = user.getLockTime().getTime();
		long currentTimeinMills = System.currentTimeMillis();
		
		if(currentTimeinMills > lockTimeinMills + UNLOCK_TIME_LIMIT) {
			
			user.setLockTime(null);
			user.setFailedLoginAttempts(0);
			user.setAccountNonLocked(true);
		
			dao.save(user);
			
			return true;
		}
		return false;
	}

	@Transactional
	@Override
	public int resetFailedLoginAttempts(Integer id) {
		return dao.resetFailedLoginAttempts(id);
	}

	@Override
	public Page<User> findByFirstNameLike(String firstName, Pageable pageable) {
		
		return dao.findByFirstNameLikeIgnoreCase(firstName, pageable);
	}

	@Override
	public void deleteById(Integer id) {
		dao.deleteById(id);
		
	}

	@Override
	public long countByEmail(String email) {
		return (long) dao.countByEmail(email);
	}

	@Transactional
	@Override
	public void deleteByIdIn(List<Integer> ids) {
		dao.deleteByIdIn(ids);
	}

	@Override
	public void verifyOldPassword(String oldPassword, String encodedPassword) throws Exception {
		if(!passwordEncoder.matches(oldPassword, encodedPassword)) {
			throw new Exception("Incorrect Old password");
		}
	}

	@Override
	public User findByEmail(String email) throws UserNotFoundException {
		User user = dao.findByEmail(email);
		
		if(user == null) {
			throw new UserNotFoundException("User Not found with the given email id");
		}
		
		return user;
	}

	@Override
	@org.springframework.transaction.annotation.Transactional
	public void sendResetPassLink(User user, String resetLink, Locale locale) throws MessagingException {
		saveUser(user);
		sendResetPasswordLink(user, resetLink, locale);
	}

	private void sendResetPasswordLink(User user, String resetLink, Locale locale) throws MessagingException {
		
		String subject = messageSource.getMessage("user.resetpassword.email.subject", null, locale);
		
		String body = emailTemplateService.getEmailTemplate("email-templates/reset-pass-link.html");
		
		emailService.sendEmail(user.getEmail(), subject, String.format(body, new Object[] {user.getFirstName(), user.getLastName(), resetLink}));
	}

	@Override
	public User findByForgotPasswordToken(String token) throws UserNotFoundException {

		User user = dao.findByForgotPasswordToken(token);

		if (user == null) {
			throw new UserNotFoundException("User Not found with the given email id");
		}

		return user;
	}

	@Override
	public Long countByEmailIdNotEqual(String email, Integer id) {
		return (long) dao.countByEmailIdNotEqual(email, id);
	}
}
