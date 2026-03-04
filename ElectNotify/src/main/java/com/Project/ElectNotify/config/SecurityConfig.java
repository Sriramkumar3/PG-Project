package com.Project.ElectNotify.config;

import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.*;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/blo/**").hasRole("BLO")
                .anyRequest().permitAll()
            )
            .formLogin(form -> form
            		.loginPage("/login")
            		.successHandler((request, response, authentication) -> {

            		    String role = authentication.getAuthorities()
            		            .iterator()
            		            .next()
            		            .getAuthority();

            		    if (role.equals("ROLE_ADMIN")) {
            		        response.sendRedirect("/admin/dashboard");
            		    } else if (role.equals("ROLE_BLO")) {
            		        response.sendRedirect("/blo/dashboard");
            		    }
            		})
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login")
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
