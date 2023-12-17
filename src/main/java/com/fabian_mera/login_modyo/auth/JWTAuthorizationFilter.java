package com.fabian_mera.login_modyo.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //Revisamos las cabeceras del request para ver si existe el token y si es valido.
        String header = request.getHeader(Helpers.HEADER_AUTHORIZATION);
        if (header == null || !header.startsWith(Helpers.TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        String[] headerSplit = header.split(" ");
        if (headerSplit.length != 2) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            // Authentication auth = jwtAuthenticationProvider.validateToken(headerSplit[1]);
            // SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (RuntimeException e) {
            // SecurityContextHolder.clearContext();
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
            throw new ServletException(e.getMessage());
        }
        filterChain.doFilter(request, response);
    }
}
