package com.dsi_group.modu_shop_bs_microservice_user.infra.security;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Slf4j
public class KeycloakAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    @Value("${keycloak-client-id}")
    String backendGatewayClientId;
    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

    @Override
    public AbstractAuthenticationToken convert(@NonNull Jwt jwt) {
        Collection<GrantedAuthority> authorities = Stream.concat(
                        jwtGrantedAuthoritiesConverter.convert(jwt).stream(),
                        extractAuthorities(jwt).stream())
                .collect(Collectors.toSet());
        return new JwtAuthenticationToken(jwt, authorities, jwt.getClaim("preferred_username"));
    }

    @SuppressWarnings("all")
    private Collection<GrantedAuthority> extractAuthorities(@NonNull Jwt jwt) {
        if (jwt.getClaim("resource_access") == null) {
            return Set.of();
        }
        Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
        Map<String, Object> clientIdRoles = null;
        if (resourceAccess.containsKey(backendGatewayClientId)) {
            clientIdRoles = (Map<String, Object>) resourceAccess.get(backendGatewayClientId);
        }
        if (clientIdRoles == null) {
            return Set.of();
        }
        Collection<String> keycloakRoles = (Collection<String>) clientIdRoles.get("roles");
        //map keycloak roles into spring security roles
        return keycloakRoles.stream()
                .map(keycloakRole -> new SimpleGrantedAuthority("ROLE_" + keycloakRole))
                .collect(Collectors.toSet());
    }
}
