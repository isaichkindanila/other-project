package ru.itis.other.project.security.details;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.itis.other.project.models.User;
import ru.itis.other.project.util.UserInfo;

import java.util.Collection;
import java.util.Collections;

public class UserDetailsImpl implements UserDetails, UserInfo {

    @Getter
    private final User user;

    private final Long id;
    private final String email;
    private final User.State state;

    @Builder
    private UserDetailsImpl(Long id, String email, User.State state) {
        this.user = null;

        this.id = id;
        this.email = email;
        this.state = state;
    }

    public UserDetailsImpl(User user) {
        this.user = user;

        this.id = null;
        this.email = null;
        this.state = null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getUsername() {
        return (user == null) ? email : user.getEmail();
    }

    @Override
    public String getPassword() {
        return (user == null) ? null : user.getPassHash();
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
        return ((user == null) ? state : user.getState()) == User.State.OK;
    }

    @Override
    public Long getId() {
        return (user == null) ? id : user.getId();
    }

    @Override
    public String getEmail() {
        return (user == null) ? email : user.getEmail();
    }

    @Override
    public String toString() {
        return "UserDetailsImpl(id=" + getId() + ", email=" + getEmail() + ")";
    }
}
