package com.example.proyectotalentotech.security;

import com.example.proyectotalentotech.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuración de componentes de aplicación relacionados con la seguridad.
 * <p>
 * Esta clase proporciona la configuración necesaria para los componentes de seguridad
 * de Spring, incluyendo servicios de detalles de usuario, proveedores de autenticación,
 * administrador de autenticación y codificador de contraseñas.
 * </p>
 * <p>
 * Los componentes configurados en esta clase son utilizados por el sistema de seguridad
 * para autenticar usuarios, verificar credenciales y administrar sesiones.
 * </p>
 * 
 * @author Equipo RedComunitaria
 * @version 1.0
 * @since 2023-03-30
 */
@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UsuarioRepository repository;

    /**
     * Configura el servicio de detalles de usuario.
     * <p>
     * Este bean proporciona un servicio que carga los detalles de usuario desde el repositorio
     * de usuarios basándose en el nombre de usuario. Es utilizado por el proveedor de autenticación
     * para cargar la información del usuario durante el proceso de autenticación.
     * </p>
     * 
     * @return Un servicio de detalles de usuario que obtiene los datos desde el repositorio
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
    }

    /**
     * Configura el proveedor de autenticación.
     * <p>
     * Este bean crea un proveedor de autenticación DAO que utiliza el servicio de detalles
     * de usuario y el codificador de contraseñas para verificar las credenciales durante
     * el proceso de autenticación.
     * </p>
     * 
     * @return Un proveedor de autenticación configurado
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Configura el administrador de autenticación.
     * <p>
     * Este bean obtiene el administrador de autenticación de la configuración proporcionada,
     * el cual es responsable de procesar las solicitudes de autenticación.
     * </p>
     * 
     * @param config La configuración de autenticación
     * @return El administrador de autenticación
     * @throws Exception Si ocurre un error al obtener el administrador de autenticación
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Configura el codificador de contraseñas.
     * <p>
     * Este bean proporciona un codificador de contraseñas que utiliza el algoritmo BCrypt
     * para codificar y verificar contraseñas de manera segura.
     * </p>
     * 
     * @return Un codificador de contraseñas BCrypt
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
} 