package com.example.proyectotalentotech.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Entidad que representa los roles de usuario en el sistema.
 * <p>
 * Esta clase mapea a la tabla 'roles' en la base de datos y almacena
 * información sobre los diferentes roles que pueden tener los usuarios
 * en el sistema, como administrador, usuario común, etc. Los roles
 * determinan los permisos y capacidades que tendrá un usuario.
 * </p>
 * 
 * @author Equipo RedComunitaria
 * @version 1.0
 * @since 2023-03-30
 */
@Entity
@Table(name = "roles")
@Data
public class Roles {

    /**
     * Identificador único del rol.
     * Se genera automáticamente al crear un nuevo registro.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idroles;

    /**
     * Fecha y hora de creación del rol.
     * No puede ser nulo y registra el momento exacto en que se creó el rol.
     */
    @Column(nullable = false)
    private LocalDateTime creado;

    /**
     * Fecha y hora de la última modificación del rol.
     * No puede ser nulo y registra el momento exacto de la última actualización.
     */
    @Column(nullable = false)
    private LocalDateTime modificado;

    /**
     * Identificador del usuario asociado al rol.
     * No puede ser nulo y debe corresponder a un ID válido en la tabla de usuarios.
     */
    @Column(nullable = false)
    private int idusuarios;

    /**
     * Identificador del tipo de usuario asociado al rol.
     * No puede ser nulo y debe corresponder a un ID válido en la tabla de tipos de usuario.
     */
    @Column(nullable = false)
    private int idtipousuario;
    
    /**
     * Nombre del rol.
     * No puede ser nulo, tiene una longitud máxima de 50 caracteres y define el rol
     * específico dentro del sistema (por ejemplo, "ROLE_ADMIN", "ROLE_USER").
     */
    @Column(name = "nombre_rol", nullable = false, length = 50)
    private String nombreRol;
}

