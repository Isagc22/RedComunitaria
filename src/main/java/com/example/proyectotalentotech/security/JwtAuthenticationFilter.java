package com.example.proyectotalentotech.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtro de autenticación basado en JWT para interceptar y procesar solicitudes HTTP.
 * <p>
 * Este filtro extiende {@link OncePerRequestFilter} para garantizar que se ejecute
 * una vez por cada solicitud. Su función principal es verificar la presencia y validez
 * de un token JWT en el encabezado de autorización de la solicitud, y si es válido,
 * autenticar al usuario y establecer su contexto de seguridad.
 * </p>
 * <p>
 * El flujo de trabajo del filtro es el siguiente:
 * <ol>
 *   <li>Extrae el token JWT del encabezado de autorización</li>
 *   <li>Valida el token utilizando el {@link JwtService}</li>
 *   <li>Carga los detalles del usuario asociado al token</li>
 *   <li>Establece la autenticación en el contexto de seguridad si el token es válido</li>
 * </ol>
 * </p>
 * 
 * @author Equipo RedComunitaria
 * @version 1.0
 * @since 2023-03-30
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    /**
     * Procesa la solicitud HTTP para extraer y validar el token JWT.
     * <p>
     * Este método es llamado por el framework Spring para cada solicitud HTTP
     * que pasa a través del filtro. Extrae el token JWT del encabezado de autorización,
     * valida su autenticidad y, si es válido, establece el contexto de seguridad
     * para el usuario correspondiente.
     * </p>
     * 
     * @param request La solicitud HTTP entrante
     * @param response La respuesta HTTP que se enviará
     * @param filterChain La cadena de filtros para continuar el procesamiento
     * @throws ServletException Si ocurre un error durante el procesamiento
     * @throws IOException Si ocurre un error de E/S durante el procesamiento
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;
        
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        
        jwt = authHeader.substring(7);
        username = jwtService.extractUsername(jwt);
        
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            
            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
} 