package ru.itis.other.project.services;

import ru.itis.other.project.util.UserInfo;

import java.util.Optional;

/**
 * Utility service for extracting {@link UserInfo} from current {@link org.springframework.security.core.Authentication}.
 */
public interface AuthService {

    /**
     * @return whether or not current request is authenticated
     */
    boolean isAuthenticated();

    /**
     * @return wrapped {@link UserInfo} if current request is authenticated, empty otherwise
     */
    Optional<UserInfo> getUserInfoOptional();

    /**
     * @return {@link UserInfo} associated with current request
     * @throws IllegalStateException if current request is not authenticated
     */
    UserInfo getUserInfo();
}
