package ru.itis.other.project.services.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.other.project.repositories.interfaces.UserInfoRepository;
import ru.itis.other.project.repositories.interfaces.UserRepository;
import ru.itis.other.project.security.details.UserDetailsImpl;

@Service
@AllArgsConstructor
@Slf4j
class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserInfoRepository infoRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));

        var info = infoRepository.findByUser(user);

        return new UserDetailsImpl(user, info);
    }
}
