package com.hexploretech.library_api.security;

import org.springframework.lang.Nullable;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Component;

import com.hexploretech.library_api.service.ClientService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomRegisteredClientRepository implements RegisteredClientRepository {
	private final ClientService clientService;
	private final ClientSettings clientSettings;
	private final TokenSettings tokenSettings;

	@Override
	public void save(RegisteredClient registeredClient) {

	}

	@Nullable
	@Override
	public RegisteredClient findById(String id) {
		return null;
	}

	@Nullable
	@Override
	public RegisteredClient findByClientId(String clientId) {
		var client = clientService.findByClientId(clientId);

		if (client == null) {
			return null;
		}

		return RegisteredClient.withId(client.getId().toString()).clientId(client.getClientId())
				.clientSecret(client.getClientSecret()).redirectUri(client.getRedirectUri()).scope(client.getScope())
				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
				.authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
				.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN).clientSettings(clientSettings)
				.tokenSettings(tokenSettings).build();
	}
}
