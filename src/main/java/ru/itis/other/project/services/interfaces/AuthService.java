package ru.itis.other.project.services.interfaces;

import org.springframework.security.access.AccessDeniedException;
import ru.itis.other.project.models.User;

/**
 * Utility service for extracting {@link User} from current {@link org.springframework.security.core.Authentication}.
 */
public interface AuthService {

    /**
     * @return whether or not current request is authenticated
     */
    boolean isAuthenticated();

    /**
     * Extracts currently authenticated user from {@link org.springframework.security.core.Authentication}.
     *
     * @return currently authenticated {@code User}
     * @throws AccessDeniedException if current request is not authenticated
     */
    User getUser();
}
