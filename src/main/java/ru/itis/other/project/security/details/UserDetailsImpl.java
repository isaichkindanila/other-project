package ru.itis.other.project.security.details;

import lombok.ToString;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.itis.other.project.models.User;
import ru.itis.other.project.models.UserInfo;

import java.util.Collection;
import java.util.Collections;

@ToString
public class UserDetailsImpl implements UserDetails {

    private final @NonNull User user;
    private final @Nullable UserInfo info;

    public UserDetailsImpl(User user, UserInfo info) {
        this.user = user;
        this.info = info;
    }

    public UserDetailsImpl(User user) {
        this(user, null);
    }

    public UserDetailsImpl(Long id, String email) {
        this(new User(id, email));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public String getPassword() {
        return (info == null) ? null : info.getPassHash();
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
        if (info == null) {
            return true;
        } else {
            return info.getState() == UserInfo.State.OK;
        }
    }

    @NonNull
    public User getUser() {
        return user;
    }
}
