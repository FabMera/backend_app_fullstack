package com.fabian_mera.login_modyo.auth;

import com.fabian_mera.login_modyo.exceptions.UnAuthorizedException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JWTAuthenticationProvider jwtAuthenticationProvider;
    private final List<String> urlsPermitidas = Arrays.asList("/login", "/register","/users");

    public JwtAuthFilter(JWTAuthenticationProvider jwtAuthenticationProvider) {
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //Revisamos las cabeceras del request para ver si existe el token y si es valido.
        String header = request.getHeader(Helpers.HEADER_AUTHORIZATION);
        if (header == null || !header.startsWith(Helpers.TOKEN_PREFIX)) {
            throw new UnAuthorizedException();
        }

        String[] headerSplit = header.split(" ");
        if (headerSplit.length != 2) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            Authentication auth = jwtAuthenticationProvider.validateToken(headerSplit[1]);
            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (RuntimeException e) {
            SecurityContextHolder.clearContext();
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
            throw new ServletException(e.getMessage());
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        //Revisamos si la url del request esta en la lista de urls permitidas
        return urlsPermitidas.stream().anyMatch(url -> request.getRequestURI().contains(url));
    }
}
