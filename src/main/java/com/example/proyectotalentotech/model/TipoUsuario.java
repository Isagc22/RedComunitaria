package com.example.proyectotalentotech.model;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Entidad que representa los tipos de usuario en el sistema.
 * <p>
 * Esta clase mapea a la tabla 'tipousuario' en la base de datos y almacena
 * información sobre los diferentes tipos de usuarios que pueden existir
 * en el sistema, como administradores, usuarios comunes, etc.
 * </p>
 * 
 * @author Equipo RedComunitaria
 * @version 1.0
 * @since 2023-03-30
 */
@Entity
@Table(name = "tipousuario")
@Data
public class TipoUsuario {
    /**
     * Identificador único del tipo de usuario.
     * Se genera automáticamente al crear un nuevo registro.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idtipousuario;

    /**
     * Nombre del tipo de usuario.
     * No puede ser nulo y tiene una longitud máxima de 100 caracteres.
     * Ejemplos: "Administrador", "Usuario Regular", "Consultor".
     */
    @Column(nullable = false, length = 100)
    private String nombre_tipo_usuario;

    /**
     * Estado del tipo de usuario (activo/inactivo).
     * Indica si el tipo de usuario está disponible para ser asignado.
     */
    @Column(nullable = false)
    private boolean estado_tipo_usuario;
}

