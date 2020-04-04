package ru.itis.other.project.services;

import ru.itis.other.project.models.User;
import ru.itis.other.project.util.UserInfo;

/**
 * Utility service for extracting {@code User} and {@code UserInfo} objects from current {@link org.springframework.security.core.Authentication}.
 */
public interface AuthService {

    /**
     * @return whether or not current request is authenticated
     */
    boolean isAuthenticated();

    /**
     * @return {@code UserInfo} associated with current request
     * @throws IllegalStateException if current request is not authenticated
     */
    UserInfo getUserInfo();

    /**
     * Loads user from database based on {@code UserInfo}.
     * @return currently authenticated {@code User}
     * @throws IllegalStateException if current request is not authenticated
     */
    User getUser();
}
