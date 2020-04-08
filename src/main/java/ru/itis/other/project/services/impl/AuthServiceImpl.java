package ru.itis.other.project.services.impl;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.itis.other.project.models.User;
import ru.itis.other.project.security.details.UserDetailsImpl;
import ru.itis.other.project.services.AuthService;

@Service
class AuthServiceImpl implements AuthService {

    @Override
    public boolean isAuthenticated() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        return !(auth instanceof AnonymousAuthenticationToken);
    }

    @Override
    public User getUser() {
        try {
            var auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth == null) {
                throw new AccessDeniedException("not authenticated");
            }

            var details = (UserDetailsImpl) auth.getPrincipal();
            return details.getUser();
        } catch (ClassCastException e) {
            // auth is AnonymousAuthenticationToken, request is not authenticated
            throw new AccessDeniedException("not authenticated");
        }
    }
}
