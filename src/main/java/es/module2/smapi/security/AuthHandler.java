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
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof KeycloakPrincipal)
            return (KeycloakPrincipal<RefreshableKeycloakSecurityContext>) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return null;
    }

    /**
     * Get the access token from the current user
     * @return AccessToken instance
     */
    public AccessToken getUserAccessToken() {
        if (getPrincipal() != null)
            return getPrincipal().getKeycloakSecurityContext().getToken();
        return null;
    }

    /**
     * Get the username from the current logged-in user
     * @return username as String
     */
    public String getUsername() {
        if (getUserAccessToken() != null)
            return getUserAccessToken().getPreferredUsername();
        return null;
    }

    /**
     * Checks if the current logged-in user is an Admin
     * @return true if the user is an Admin, false otherwise
     */
    public boolean isAdmin() {
        if (getUserAccessToken() != null)
            return getUserAccessToken().getResourceAccess(keycloakClientId).getRoles().contains("admin");
        return false;
    }

    /**
     * Checks if the current logged-in user is a User
     * @return true if the user is a User, false otherwise
     */
    public boolean isUser() {
        if (getUserAccessToken() != null)
            return getUserAccessToken().getResourceAccess(keycloakClientId).getRoles().contains("user");
        return false;
    }

}
