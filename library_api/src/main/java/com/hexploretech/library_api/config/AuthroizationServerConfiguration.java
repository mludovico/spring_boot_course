package com.hexploretech.library_api.config;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.List;
import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.web.SecurityFilterChain;

import com.hexploretech.library_api.security.CustomAuthentication;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration
@EnableWebSecurity
public class AuthroizationServerConfiguration {

	@Bean
	@Order(1)
	public SecurityFilterChain authServerSecurityFilterChain(HttpSecurity http) throws Exception {
		OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
		http.getConfigurer(OAuth2AuthorizationServerConfigurer.class).oidc(Customizer.withDefaults());
		http.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
		http.formLogin(configurer -> configurer.loginPage("/login"));
		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}

	@Bean
	public TokenSettings tokenSettings() {
		return TokenSettings.builder().accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
				.accessTokenTimeToLive(java.time.Duration.ofMinutes(1))
				.refreshTokenTimeToLive(java.time.Duration.ofMinutes(10)).build();
	}

	@Bean
	public ClientSettings clientSettings() {
		return ClientSettings.builder().requireAuthorizationConsent(false).build();
	}

	public JWKSource<SecurityContext> jwkSource() throws Exception {
		RSAKey rsaKey = generateRSAKey();
		JWKSet rsaSet = new JWKSet(rsaKey);
		return new ImmutableJWKSet<>(rsaSet);
	}

	private RSAKey generateRSAKey() throws Exception {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		keyPairGenerator.initialize(2048);
		KeyPair keyPair = keyPairGenerator.generateKeyPair();

		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

		String keyId = UUID.randomUUID().toString();
		System.out.println("Generated RSA Key with ID: " + keyId);
		return new RSAKey.Builder(publicKey).privateKey(privateKey).keyID(keyId).build();
	}

	public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
		return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
	}

	@Bean
	public AuthorizationServerSettings authorizationServerSettings() {
		return AuthorizationServerSettings.builder().build();

	}

	@Bean
	public OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer() {
		return context -> {
			var principal = context.getPrincipal();

			if (principal instanceof CustomAuthentication authentication) {
				OAuth2TokenType tokenType = context.getTokenType();

				if (tokenType == OAuth2TokenType.ACCESS_TOKEN) {
					List<String> authorities = authentication.getAuthorities().stream()
							.map(GrantedAuthority::getAuthority).toList();

					context.getClaims().claim("authorities", authorities)
							.claim("email", authentication.getUser().getEmail());
				}
			}
		};
	}
}
