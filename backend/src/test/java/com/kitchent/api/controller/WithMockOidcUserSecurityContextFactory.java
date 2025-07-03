package com.kitchent.api.controller;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.time.Instant;
import java.util.Collections;
import java.util.Map;

public class WithMockOidcUserSecurityContextFactory implements WithSecurityContextFactory<WithMockOidcUser> {

    @Override
    public SecurityContext createSecurityContext(WithMockOidcUser annotation) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        // Create ID token claims
        Map<String, Object> claims = Map.of(
            "sub", annotation.sub(),
            "email", annotation.email(),
            "name", annotation.name(),
            "iss", "https://accounts.google.com",
            "aud", "test-audience",
            "iat", Instant.now().getEpochSecond(),
            "exp", Instant.now().plusSeconds(3600).getEpochSecond()
        );

        // Create OIDC ID Token
        OidcIdToken idToken = new OidcIdToken(
            "test-token-value",
            Instant.now(),
            Instant.now().plusSeconds(3600),
            claims
        );

        // Create OIDC User
        OidcUser oidcUser = new DefaultOidcUser(
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")),
            idToken,
            "email"
        );

        // Create OAuth2 Authentication Token
        OAuth2AuthenticationToken authToken = new OAuth2AuthenticationToken(
            oidcUser,
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")),
            "google"
        );

        context.setAuthentication(authToken);
        return context;
    }
}
