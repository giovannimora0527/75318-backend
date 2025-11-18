package com.uniminuto.clinica.security;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletResponse;

/**
 * Clase de configuracion para la seguridad.
 *
 * @author lmora
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtTokenFilter jwtTokenFilter;

   /**
     * Filtro de seguridad.
     *
     * @param http peticion de entrada.
     * @return Autorizado.
     * @throws Exception Excepcion.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        http
                .cors() // Habilita CORS
                .and()
                .csrf().disable() // Deshabilita CSRF si estás probando con Postman
                .authorizeHttpRequests((requests) -> requests
                    // Permitir acceso sin autenticación solo a login y recuperar-contrasena
                    .antMatchers("/auth/login", "/auth/recuperar-contrasena").permitAll()
                    // Permitir acceso a Swagger/OpenAPI
                    .antMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**", "/swagger-resources/**", "/webjars/**").permitAll()
                    // Permitir acceso al endpoint de documentación del sistema
                    .antMatchers("/documentacion/**").permitAll()
                    // Permitir acceso a las imágenes de diagramas
                    .antMatchers("/diagramas/**").permitAll()
                    // El resto de endpoints requieren autenticación
                    .anyRequest().authenticated()
                )
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling((exceptions) -> exceptions
                    .authenticationEntryPoint((request, response, authException) -> {
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        response.setContentType("application/json");
                        response.setCharacterEncoding("UTF-8");
                        response.getWriter().write("{\"error\":\"UNAUTHORIZED\",\"message\":\"No autenticado. Por favor, inicie sesión.\",\"mensaje\":\"No autenticado. Por favor, inicie sesión.\"}");
                    })
                    .accessDeniedHandler((request, response, accessDeniedException) -> {
                        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                        response.setContentType("application/json");
                        response.setCharacterEncoding("UTF-8");
                        response.getWriter().write("{\"error\":\"FORBIDDEN\",\"message\":\"Acceso denegado. No tiene permisos para acceder a este recurso.\",\"mensaje\":\"Acceso denegado. No tiene permisos para acceder a este recurso.\"}");
                    })
                )
                .logout((logout) -> logout.permitAll());

        return http.build();
    }

    /**
     * Configuracion del cors.
     *
     * @return configuracion.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of(
                "http://localhost:4200",
                "http://localhost:8000",
                "http://localhost:8080",
                "http://127.0.0.1:8080",
                "http://127.0.0.1:4200",
                "http://127.0.0.1:8000"));

        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*", "Authorization", "Content-Type"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

}
