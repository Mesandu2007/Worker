package com.example.Worker.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }


    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {


       

        String authHeader = request.getHeader("Authorization");



        if (authHeader == null || !authHeader.startsWith("Bearer ")) {

            filterChain.doFilter(request, response);
            return;
        }


    

        String token = authHeader.substring(7);


        try {

       

            if (!jwtService.isTokenValid(token)) {

                filterChain.doFilter(request, response);
                return;
            }


           

            String email = jwtService.extractEmail(token);

            Long id = jwtService.extractId(token);

            String role = jwtService.extractRole(token);


        

            SimpleGrantedAuthority authority =
                    new SimpleGrantedAuthority("ROLE_" + role);


    

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            email,
                            null,
                            Collections.singletonList(authority)
                    );



            authentication.setDetails(id);


      

            SecurityContextHolder
                    .getContext()
                    .setAuthentication(authentication);


        } catch (Exception e) {

            
            SecurityContextHolder.clearContext();
        }



        filterChain.doFilter(request, response);
    }
}