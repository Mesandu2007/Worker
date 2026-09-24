package com.example.Worker.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }


    

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http

                // Disable CSRF because we are using JWT
                .csrf(csrf -> csrf.disable())


                // Configure endpoint permissions
                .authorizeHttpRequests(auth -> auth

                    

                        .requestMatchers(
                                "/api/auth/**",
                                "/api/services/**"
                        ).permitAll()


                 

                        .requestMatchers(
                                "/api/customers/**"
                        ).hasRole("CUSTOMER")



                        .requestMatchers(
                                "/api/workers/me"
                        ).hasRole("WORKER")


                   

                        .requestMatchers(
                                "/api/jobs/**",
                                "/api/reviews/**",
                                "/api/notifications/**",
                                "/api/conversations/**",
                                "/api/messages/**"
                        ).authenticated()


                  

                        .requestMatchers(
                                "/api/workers",
                                "/api/workers/*",
                                "/api/workers/*/reviews"
                        ).permitAll()


                   

                        .anyRequest().authenticated()
                )


          
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )


          

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );


        return http.build();
    }




    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }



    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration)
            throws Exception {

        return configuration.getAuthenticationManager();
    }
}