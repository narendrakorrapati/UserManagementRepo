package com.narendra.bss.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.narendra.bss.entity.User;


public interface UserDao extends PagingAndSortingRepository<User, Integer>{

	Optional<User> findById(Integer id);
	
	Page<User> findAllCustom(Pageable pageable);
	
	void deleteByEmail(String email);
	
	@Query(value = "update com.narendra.bss.entity.User u set u.failedLoginAttempts = :failedAttempts where u.id = :id")
	@Modifying
	int updatefailedLoginAttempts(@Param("failedAttempts") Integer failedAttempts, @Param("id") Integer id);

	@Query(value = "update com.narendra.bss.entity.User u set u.failedLoginAttempts = 0 where u.id = :id")
	@Modifying
	int resetFailedLoginAttempts(@Param("id") Integer id);
	
	Page<User> findByFirstNameLikeIgnoreCase(@Param("firstName") String firstName, Pageable pageable);
	
	Object countByEmail(@Param("email") String email);
	
	@Modifying
	void deleteByIdIn(List<Integer> ids);
	
	User findByEmail(@Param("email") String email);

	User findByForgotPasswordToken(String token);

	Object countByEmailIdNotEqual(@Param("email") String email, @Param("id") Integer id);
}

