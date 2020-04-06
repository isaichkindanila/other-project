package ru.itis.other.project.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.web.filter.GenericFilterBean;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Configuration
    @Order(0)
    @AllArgsConstructor
    public static class ApiConfig extends WebSecurityConfigurerAdapter {

        private GenericFilterBean jwtAuthFilter;
        private AuthenticationProvider jwtAuthProvider;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            // this configuration should only be used for API requests
            http.antMatcher("/api/**");

            // CSRF protection and default auth methods are useless
            http.csrf().disable();
            http.httpBasic().disable();
            http.formLogin().disable();
            http.logout().disable();

            // sessions should be disabled for API
            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

            // token filter should be placed BEFORE anonymous filter to allow anonymous requests
            http.addFilterBefore(jwtAuthFilter, AnonymousAuthenticationFilter.class);
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) {
            auth.authenticationProvider(jwtAuthProvider);
        }
    }

    @Configuration
    @Order(1)
    @AllArgsConstructor
    public static class GeneralConfig extends WebSecurityConfigurerAdapter {

        private final UserDetailsService userDetailsService;
        private final PasswordEncoder passwordEncoder;

        @Override
        public void configure(WebSecurity web) {
            // disable security for static files
            web.ignoring().antMatchers("/static/**");
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            // to use permitAll()
            http.authorizeRequests();

            http.formLogin()
                    .loginPage("/signIn")
                    .usernameParameter("email")
                    .permitAll();

            http.logout()
                    .logoutUrl("/signOut")
                    .permitAll();

            http.csrf().ignoringAntMatchers("/signOut");
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        }
    }
}
