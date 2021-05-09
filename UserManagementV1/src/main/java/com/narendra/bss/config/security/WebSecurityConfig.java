package com.narendra.bss.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	@Qualifier("customUserDetailsService")
	UserDetailsService userDetailsService;
	
	@Autowired
	private CustomLoginFailedHandler loginFailedHandler;
	
	@Autowired
	private CustomLoginSuccessHandler loginSuccessHandler;
	
	@Autowired
	private CustomLogoutSuccessHandler logoutSuccessHandler;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(userDetailsService);
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf()
			.and().authorizeRequests().anyRequest().authenticated()
				.withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
					public <O extends FilterSecurityInterceptor> O postProcess(O fsi) {
						FilterInvocationSecurityMetadataSource newSource = new DbFilterInvocationSecurityMetadataSource();
						fsi.setSecurityMetadataSource(newSource);
						fsi.setAccessDecisionManager(new MyAccessDecisionManager());
						return fsi;
					}
				})
			.and().formLogin().loginPage("/login").loginProcessingUrl("/login")
			.defaultSuccessUrl("/dashboard", true).usernameParameter("email").passwordParameter("password")
			.failureHandler(loginFailedHandler)
			.successHandler(loginSuccessHandler)
			.and().logout().logoutSuccessHandler(logoutSuccessHandler)
			.invalidateHttpSession(true).deleteCookies("JSESSIONID")
			.and()
			.exceptionHandling()
			.accessDeniedHandler(accessDeniedHandler());
		
		http.sessionManagement().maximumSessions(1).maxSessionsPreventsLogin(true).sessionRegistry(sessionRegistry());
		
		http.sessionManagement().invalidSessionStrategy(new CustomInvalidSessionStrategy());
		
	}
	
	///For restricting the concurrent login of the same user.
	@Bean
	public SessionRegistry sessionRegistry() {
	    SessionRegistry sessionRegistry = new SessionRegistryImpl();
	    
		/*
		 * List<Object> principels = sessionRegistry.getAllPrincipals();
		 * 
		 * for(Object principal : principels) { List<SessionInformation> sessionInfoList
		 * = sessionRegistry.getAllSessions(principal, false); SessionInformation info =
		 * sessionInfoList.get(0); info.expireNow(); }
		 */
	    return sessionRegistry;
	}
	
	@Bean
	public AccessDeniedHandler accessDeniedHandler(){
	    return new CustomAccessDeniedHandler();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}
	
	
}