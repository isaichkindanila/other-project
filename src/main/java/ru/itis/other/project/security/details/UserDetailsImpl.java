package ru.itis.other.project.security.details;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.itis.other.project.models.User;
import ru.itis.other.project.util.UserInfo;

import java.util.Collection;
import java.util.Collections;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(exclude = "passHash")
public class UserDetailsImpl implements UserDetails, UserInfo {

    private final Long id;
    private final String email;
    private final String passHash;
    private final User.State state;

    public UserDetailsImpl(User user) {
        id = user.getId();
        state = user.getState();
        email = user.getEmail();
        passHash = user.getPassHash();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return passHash;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return state == User.State.OK;
    }
}
