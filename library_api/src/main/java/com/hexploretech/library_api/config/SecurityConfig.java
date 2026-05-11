package com.hexploretech.library_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

import com.hexploretech.library_api.security.JwtCustomAuthenticationFilter;
import com.hexploretech.library_api.security.SocialLoginSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http,
			SocialLoginSuccessHandler socialLoginSuccessHandler,
			JwtCustomAuthenticationFilter jwtCustomAuthenticationFilter) throws Exception {
		return http.csrf(AbstractHttpConfigurer::disable).formLogin(configurer -> configurer.loginPage("/login"))
				.authorizeHttpRequests(authorizeRequests -> {
					authorizeRequests.requestMatchers("/login/**").permitAll();
					authorizeRequests.requestMatchers(HttpMethod.POST, "/users/**").permitAll();
					authorizeRequests.anyRequest().authenticated();
				}).oauth2Login(oauth2 -> oauth2.loginPage("/login").successHandler(socialLoginSuccessHandler))
				.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
				.addFilterAfter(jwtCustomAuthenticationFilter, BearerTokenAuthenticationFilter.class).build();
	}

	/// Configures prefix role in @PreAuthorize annotation
	@Bean
	public GrantedAuthorityDefaults grantedAuthorityDefaults() {
		return new GrantedAuthorityDefaults(""); // Remove the ROLE_ prefix
	}

	/// Configures prefix scope in JWT token
	@Bean
	public JwtAuthenticationConverter jwtAuthenticationConverter() {
		var authoritiesConverter = new JwtGrantedAuthoritiesConverter();
		authoritiesConverter.setAuthorityPrefix("");
		var converter = new JwtAuthenticationConverter();
		converter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);
		return converter;
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return web -> web.ignoring()
				.requestMatchers("/v2/api-docs/**", "v3/api-docs/**", "/swagger-resources/**", "/swagger-ui.html",
						"/swagger-ui/**", "/webjars/**");
	}
}
