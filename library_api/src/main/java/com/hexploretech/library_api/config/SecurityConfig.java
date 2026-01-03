package com.hexploretech.library_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.hexploretech.library_api.security.CustomUserDetailService;
import com.hexploretech.library_api.service.UserService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.csrf(AbstractHttpConfigurer::disable).formLogin(Customizer.withDefaults())
				.httpBasic(Customizer.withDefaults())
				.formLogin(configurer -> configurer.loginPage("/login").permitAll())
				.authorizeHttpRequests(authorizeRequests -> {
					authorizeRequests.requestMatchers("/login/**").permitAll();
					authorizeRequests.anyRequest().authenticated();
				}).build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}

	@Bean
	public UserDetailsService userDetailsService(UserService userService) {
		return new CustomUserDetailService(userService);
	}
}
