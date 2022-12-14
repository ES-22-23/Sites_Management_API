package es.module2.smapi.security;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.RefreshableKeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthHandler {

    @Value("${keycloak.resource}")
    private String keycloakClientId;

    @SuppressWarnings("unchecked")
    public KeycloakPrincipal<RefreshableKeycloakSecurityContext> getPrincipal() {
        return (KeycloakPrincipal<RefreshableKeycloakSecurityContext>) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    /**
     * Get the access token from the current user
     * @return AccessToken instance
     */
    public AccessToken getUserAccessToken() {
        return getPrincipal().getKeycloakSecurityContext().getToken();
    }

    /**
     * Get the username from the current logged-in user
     * @return username as String
     */
    public String getUsername() {
        return getUserAccessToken().getPreferredUsername();
    }

    /**
     * Checks if the current logged-in user is an Admin
     * @return true if the user is an Admin, false otherwise
     */
    public boolean isAdmin() {
        return getUserAccessToken().getResourceAccess(keycloakClientId)
                .getRoles()
                .contains("admin");
    }

    /**
     * Checks if the current logged-in user is a User
     * @return true if the user is a User, false otherwise
     */
    public boolean isUser() {
        return getUserAccessToken().getResourceAccess(keycloakClientId)
                .getRoles()
                .contains("user");
    }

}
