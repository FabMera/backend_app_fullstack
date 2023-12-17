package com.fabian_mera.login_modyo.auth;

import com.fabian_mera.login_modyo.exceptions.AccessDeniedHandlerException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static com.fabian_mera.login_modyo.auth.URLFrontEnd.URL_FRONTEND_SESSION;
import static com.fabian_mera.login_modyo.auth.Helpers.CONTENT_TYPE;
import static com.fabian_mera.login_modyo.auth.Helpers.HEADER_AUTHORIZATION;
import static com.fabian_mera.login_modyo.auth.Helpers.LOGIN_URL;


@Configuration
@EnableWebSecurity
public class SpringConfigurityConfig {
    private final AccessDeniedHandlerException accessDeniedHandlerException;

    public SpringConfigurityConfig(AccessDeniedHandlerException accessDeniedHandlerException) {
        this.accessDeniedHandlerException = accessDeniedHandlerException;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers(LOGIN_URL, "/users/**", "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**")
                                .permitAll()
                                .requestMatchers(HttpMethod.GET, "/users/**").hasAnyRole(Roles.USUARIO_DEV, Roles.ADMIN)
                                .requestMatchers(HttpMethod.POST, "/users/**").hasAnyRole(Roles.ADMIN, Roles.USUARIO_DEV)
                                .requestMatchers(HttpMethod.POST, "/login").hasAnyRole(Roles.ADMIN, Roles.USUARIO_DEV)
                                .anyRequest().authenticated());
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(URL_FRONTEND_SESSION));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList(HEADER_AUTHORIZATION, CONTENT_TYPE));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

