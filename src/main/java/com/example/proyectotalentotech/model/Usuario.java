package com.example.proyectotalentotech.model;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Entidad que representa a un usuario del sistema.
 * <p>
 * Esta clase mapea a la tabla 'usuarios' en la base de datos y almacena
 * información de autenticación y estado del usuario. Implementa UserDetails
 * para integrarse con el sistema de seguridad de Spring Security.
 * </p>
 * 
 * @author Equipo RedComunitaria
 * @version 1.0
 * @since 2023-03-30
 */
@Entity
@Table(name = "usuarios")
@Data
public class Usuario implements UserDetails {
    /**
     * Identificador único del usuario.
     * Se genera automáticamente al crear un nuevo registro.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idusuarios;
    
    /**
     * Correo electrónico del usuario.
     * No puede ser nulo, debe ser único y tiene una longitud máxima de 255 caracteres.
     */
    @Column(name = "email_user", nullable = false, unique = true, length = 255)
    private String emailUser;
    
    /**
     * Contraseña del usuario.
     * No puede ser nula y tiene una longitud máxima de 255 caracteres.
     */
    @Column(nullable = false, length = 255)
    private String password_user;
    
    /**
     * Estado del usuario (activo/inactivo).
     * Determina si el usuario tiene permiso para acceder al sistema.
     */
    @Column(nullable = false)
    private Boolean estado_user;
    
    /**
     * Nombre de usuario para iniciar sesión.
     * No puede ser nulo, debe ser único y tiene una longitud máxima de 50 caracteres.
     */
    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;
    
    /**
     * Rol asignado al usuario.
     * Define los permisos y capacidades del usuario en el sistema.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idroles")
    private Roles rol;

    /**
     * Retorna las autoridades asignadas al usuario.
     * Implementación del método de la interfaz UserDetails.
     * 
     * @return Colección de autoridades (roles) del usuario
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(rol.getNombreRol()));
    }

    /**
     * Retorna la contraseña del usuario.
     * Implementación del método de la interfaz UserDetails.
     * 
     * @return Contraseña del usuario
     */
    @Override
    public String getPassword() {
        return password_user;
    }

    /**
     * Retorna el nombre de usuario.
     * Implementación del método de la interfaz UserDetails.
     * 
     * @return Nombre de usuario
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * Verifica si la cuenta del usuario no ha expirado.
     * Implementación del método de la interfaz UserDetails.
     * En esta implementación, las cuentas nunca expiran.
     * 
     * @return true siempre, indicando que la cuenta no ha expirado
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Verifica si la cuenta del usuario no está bloqueada.
     * Implementación del método de la interfaz UserDetails.
     * En esta implementación, las cuentas nunca se bloquean.
     * 
     * @return true siempre, indicando que la cuenta no está bloqueada
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Verifica si las credenciales del usuario no han expirado.
     * Implementación del método de la interfaz UserDetails.
     * En esta implementación, las credenciales nunca expiran.
     * 
     * @return true siempre, indicando que las credenciales no han expirado
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Verifica si el usuario está habilitado.
     * Implementación del método de la interfaz UserDetails.
     * La habilitación se determina por el campo estado_user.
     * 
     * @return true si el usuario está habilitado, false en caso contrario
     */
    @Override
    public boolean isEnabled() {
        return estado_user;
    }
}

