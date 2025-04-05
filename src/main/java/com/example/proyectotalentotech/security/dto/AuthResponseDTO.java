package com.example.proyectotalentotech.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) para respuestas de autenticación.
 * <p>
 * Esta clase se utiliza para devolver información sobre una autenticación exitosa 
 * al cliente, incluyendo el token JWT generado y datos básicos del usuario autenticado.
 * Esta respuesta se envía después de un inicio de sesión o registro exitoso.
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
public class AuthResponseDTO {
    /**
     * Token JWT generado para el usuario autenticado.
     * Este token debe ser utilizado en las cabeceras de autorización
     * para acceder a recursos protegidos.
     */
    private String token;
    
    /**
     * Tipo de token generado, por defecto "Bearer".
     * Indica el tipo de autenticación que debe utilizarse
     * al incluir el token en las solicitudes.
     */
    private String tipo = "Bearer";
    
    /**
     * Identificador único del usuario autenticado.
     * Este valor puede ser utilizado para operaciones específicas
     * relacionadas con el usuario.
     */
    private Integer idUsuario;
    
    /**
     * Nombre de usuario del usuario autenticado.
     * Utilizado principalmente para mostrar información en la interfaz de usuario.
     */
    private String username;
    
    /**
     * Rol asignado al usuario autenticado.
     * Determina los permisos y capacidades del usuario dentro del sistema.
     */
    private String rol;
} 