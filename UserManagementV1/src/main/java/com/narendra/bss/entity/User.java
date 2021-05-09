package com.narendra.bss.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name="USERS")
@NamedQueries({
		@NamedQuery(name = User.FIND_BY_EMAIL, query = "select u from User u join fetch u.userRoles r join fetch r.menus m join fetch m.childs c where u.email = :email"),
		@NamedQuery(name = User.FIND_ALL, query = "select u from User u join fetch u.userRoles"),
		@NamedQuery(name = User.FIND_BY_FIRST_NAME_IGNORECASE, query = "select u from User u join fetch u.userRoles r where lower(u.firstName) like lower(concat('%', :firstName,'%'))"),
		@NamedQuery(name = User.COUNT_BY_EMAIL_WHERE_ID_NOT_EQUAL, query = "select count(*) from User u where u.email = :email and u.id <> :id")
	})

public class User implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public static final String FIND_BY_EMAIL = "User.findByEmail";
	public static final String FIND_ALL = "User.findAllCustom";
	public static final String FIND_BY_FIRST_NAME_IGNORECASE = "User.findByFirstNameLikeIgnoreCase";
	public static final String COUNT_BY_EMAIL_WHERE_ID_NOT_EQUAL = "User.countByEmailIdNotEqual";
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	
	@Column(name="PASSWORD", nullable=false)
	private String password;
		
	@Column(name="FIRST_NAME", nullable=false)
	private String firstName;

	@Column(name="LAST_NAME", nullable=false)
	private String lastName;

	@Column(name="EMAIL", nullable=false, unique = true)
	private String email;
	
	@Column(name = "account_non_locked", nullable = false)
	private boolean accountNonLocked = true;
	
	@Column(name = "failed_attempts", nullable = false)
	private int failedLoginAttempts;
	
	@Column(name = "enabled", nullable = false)
	private boolean enabled = true;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "locktime")
	private Date lockTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_pswd_time")
	private Date lastPasswordTime = new Date();
	
	@Column(name = "firstTimeLogin")
	private boolean firstTimeLogin = true;
	
	@Column(name = "reset_token", length = 50)
	private String forgotPasswordToken;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "ROLES_USERS", 
             joinColumns = { @JoinColumn(name = "USER_ID") }, 
             inverseJoinColumns = { @JoinColumn(name = "ROLE_ID") })
	@OrderBy("roleName ASC")
	private Set<UserRole> userRoles = new HashSet<UserRole>();
	
	public User() {
		
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public int getFailedLoginAttempts() {
		return failedLoginAttempts;
	}

	public void setFailedLoginAttempts(int failedLoginAttempts) {
		this.failedLoginAttempts = failedLoginAttempts;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public Date getLockTime() {
		return lockTime;
	}

	public void setLockTime(Date lockTime) {
		this.lockTime = lockTime;
	}

	public Set<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
		
	}
	
	public Date getLastPasswordTime() {
		return lastPasswordTime;
	}

	public void setLastPasswordTime(Date lastPasswordTime) {
		this.lastPasswordTime = lastPasswordTime;
	}

	public boolean isFirstTimeLogin() {
		return firstTimeLogin;
	}

	public void setFirstTimeLogin(boolean firstTimeLogin) {
		this.firstTimeLogin = firstTimeLogin;
	}
	
	public String getForgotPasswordToken() {
		return forgotPasswordToken;
	}

	public void setForgotPasswordToken(String forgotPasswordToken) {
		this.forgotPasswordToken = forgotPasswordToken;
	}

	public boolean isPasswordExpired() {
		
		Long passwordExpireDays = 30L;
		
		if(passwordExpireDays == 0 || getLastPasswordTime() == null) {
			return false;
		}
		
		Long passwordExpireTime = passwordExpireDays * 24L * 60L * 60L * 1000L;
		Long currentTimeInMills = System.currentTimeMillis();
		Long lastPasswordTime = getLastPasswordTime().getTime();
		
		return currentTimeInMills > passwordExpireTime + lastPasswordTime;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof User))
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", password=" + password
				+ ", firstName=" + firstName + ", lastName=" + lastName
				+ ", email=" + email + "]";
	}
	
}
