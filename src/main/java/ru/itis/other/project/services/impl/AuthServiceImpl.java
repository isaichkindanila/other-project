package ru.itis.other.project.services.impl;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.itis.other.project.services.AuthService;
import ru.itis.other.project.util.UserInfo;

@Service
class AuthServiceImpl implements AuthService {

    @Override
    public boolean isAuthenticated() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        return !(auth instanceof AnonymousAuthenticationToken);
    }

    @Override
    public UserInfo getUserInfo() {
        try {
            return (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (ClassCastException e) {
            throw new IllegalStateException("current request is not authenticated");
        }
    }
}
