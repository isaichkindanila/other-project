package ru.itis.other.project.services.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.itis.other.project.services.AuthService;
import ru.itis.other.project.util.UserInfo;

import java.util.Optional;

@Service
class AuthServiceImpl implements AuthService {

    @Override
    public boolean isAuthenticated() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        return auth != null && auth.isAuthenticated();
    }

    @Override
    public Optional<UserInfo> getUserInfoOptional() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            return Optional.empty();
        } else {
            return Optional.of((UserInfo) auth.getPrincipal());
        }
    }

    @Override
    public UserInfo getUserInfo() {
        return getUserInfoOptional().orElseThrow(
                () -> new IllegalStateException("current request is not authenticated")
        );
    }
}
