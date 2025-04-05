package com.example.proyectotalentotech.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

/**
 * Configuración de seguridad para la aplicación.
 * <p>
 * Esta clase define la configuración de seguridad de Spring Security para la aplicación,
 * incluyendo reglas de autorización para rutas específicas, políticas de sesiones,
 * configuración CORS (Cross-Origin Resource Sharing), y la integración del filtro
 * de autenticación JWT.
 * </p>
 * <p>
 * Las principales características de esta configuración son:
 * <ul>
 *   <li>Desactivación de CSRF para permitir solicitudes desde aplicaciones frontend</li>
 *   <li>Configuración de CORS para permitir solicitudes desde orígenes específicos</li>
 *   <li>Definición de rutas públicas y protegidas</li>
 *   <li>Configuración de política de sesiones sin estado (stateless)</li>
 *   <li>Integración del filtro de autenticación JWT</li>
 * </ul>
 * </p>
 * 
 * @author Equipo RedComunitaria
 * @version 1.0
 * @since 2023-03-30
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    /**
     * Configura la cadena de filtros de seguridad.
     * <p>
     * Este método define las reglas de seguridad para la aplicación, incluyendo:
     * <ul>
     *   <li>Desactivación de CSRF</li>
     *   <li>Configuración de CORS</li>
     *   <li>Reglas de autorización para diferentes rutas</li>
     *   <li>Política de sesiones sin estado</li>
     *   <li>Integración del proveedor de autenticación</li>
     *   <li>Adición del filtro de autenticación JWT</li>
     * </ul>
     * </p>
     * 
     * @param http El objeto HttpSecurity a configurar
     * @return La cadena de filtros de seguridad configurada
     * @throws Exception Si ocurre un error durante la configuración
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/usuarios/**").permitAll()
                        .requestMatchers("/datosPersonales/**").permitAll()
                        .requestMatchers("/emprendimientos/**").permitAll()
                        .requestMatchers("/roles/**").permitAll()
                        .requestMatchers("/regiones/**").permitAll()
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        .anyRequest().permitAll()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Configura la fuente de configuración CORS.
     * <p>
     * Este método define la configuración de CORS para permitir solicitudes desde
     * orígenes específicos, con métodos HTTP específicos y encabezados permitidos.
     * La configuración permite solicitudes desde diferentes instancias de localhost
     * en diferentes puertos, lo que facilita el desarrollo y pruebas.
     * </p>
     * 
     * @return La fuente de configuración CORS
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:3001", "http://localhost:3002", "http://127.0.0.1:3000", "http://127.0.0.1:3001", "http://127.0.0.1:3002"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
} 