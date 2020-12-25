package com.pci.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http
			.authorizeRequests()
				.antMatchers("/css/**","/js/**","/images/**").permitAll()
				.antMatchers("/Mgr/**").hasRole("MANAGER")
				.antMatchers("/Staff/**").hasRole("USER")
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage("/login")
				.defaultSuccessUrl("/index", true)
				.failureUrl("/login-error")
				.permitAll()
				.and()
			.logout()
				.logoutSuccessUrl("/login")
				.permitAll();
	}
	
	/**
	 * AuthenticationProviderの設定を行う
	 * @param auth
	 * @throws Exception
	 */
	@Autowired
	void configureAuthenthicationManager(AuthenticationManagerBuilder auth) throws Exception{
		auth
		.userDetailsService(userDetailsService)
		.passwordEncoder(passwordEncoder());
	}
	
	/**
	 * パスワードのハッシュ化
	 */
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}