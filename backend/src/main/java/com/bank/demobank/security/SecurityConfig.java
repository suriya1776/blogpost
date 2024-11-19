package com.bank.demobank.security;  // Package name

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .anyRequest().permitAll()  // Allow all requests without authentication
            .and()
            .csrf().disable()  // Disable CSRF protection
            .sessionManagement()
                .sessionCreationPolicy(org.springframework.security.config.http.SessionCreationPolicy.STATELESS); // Stateless session (no sessions)
        
        return http.build();
    }

    // @Bean
    // public UserDetailsService userDetailsService() {
    //     return new InMemoryUserDetailsManager(
    //         User.withUsername("user")
    //             .password(NoOpPasswordEncoder.getInstance().encode("password"))  // Use NoOpPasswordEncoder for testing purposes (do not use in production)
    //             .roles("USER")
    //             .build()
    //     );
    // }
}