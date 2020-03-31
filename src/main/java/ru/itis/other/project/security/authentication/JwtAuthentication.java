package ru.itis.other.project.security.authentication;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@RequiredArgsConstructor
public class JwtAuthentication implements Authentication {

    private final String token;

    @Setter
    private boolean isAuthenticated = false;

    @Setter
    private UserDetails userDetails;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (userDetails == null) {
            return Collections.emptyList();
        } else {
            return userDetails.getAuthorities();
        }
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return userDetails;
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public String getName() {
        return token;
    }
}
