package ru.itis.other.project.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.other.project.models.User;
import ru.itis.other.project.repositories.UserRepository;
import ru.itis.other.project.security.details.UserDetailsImpl;
import ru.itis.other.project.services.AuthService;
import ru.itis.other.project.util.UserInfo;

@Service
@AllArgsConstructor
class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    @Override
    public boolean isAuthenticated() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
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

    @Override
    @Transactional
    public User getUser() {
        var userInfo = (UserDetailsImpl) getUserInfo();

        if (userInfo.getUser() == null) {
            return userRepository.findById(userInfo.getId()).orElseThrow(
                    () -> new IllegalStateException("request is authenticated but user is not found in database")
            );
        } else {
            return userInfo.getUser();
        }
    }
}
