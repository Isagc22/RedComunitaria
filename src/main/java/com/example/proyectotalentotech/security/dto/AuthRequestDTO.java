package com.example.proyectotalentotech.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) para solicitudes de autenticación.
 * <p>
 * Esta clase se utiliza para transferir credenciales de usuario entre el cliente y el servidor
 * durante procesos de autenticación (inicio de sesión) y registro. Contiene los campos básicos
 * necesarios para identificar y autenticar a un usuario en el sistema.
 * </p>
 * 
 * @author Equipo RedComunitaria
 * @version 1.0
 * @since 2023-03-30
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequestDTO {
    /**
     * Nombre de usuario o correo electrónico para la autenticación.
     * Este campo puede contener tanto el nombre de usuario como el correo electrónico,
     * ya que el sistema admite ambos para la autenticación.
     */
    private String username;
    
    /**
     * Contraseña del usuario para la autenticación.
     * La contraseña se envía como texto plano en el DTO, pero debe ser manejada
     * de forma segura durante el proceso de autenticación.
     */
    private String password;
} 