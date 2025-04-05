package com.example.proyectotalentotech.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

/**
 * Entidad que representa los comentarios y calificaciones realizados a los emprendimientos.
 * <p>
 * Esta clase mapea a la tabla 'comentariosycalificaciones' en la base de datos y almacena
 * información sobre las opiniones y evaluaciones que los usuarios han realizado sobre
 * los emprendimientos, incluyendo el texto del comentario, la calificación numérica,
 * la fecha de registro, etc.
 * </p>
 * 
 * @author Equipo RedComunitaria
 * @version 1.0
 * @since 2023-03-30
 */
@Entity
@Table(name = "comentariosycalificaciones")
@Data
public class ComentariosYCalificaciones {
    /**
     * Identificador único del comentario y calificación.
     * Se genera automáticamente al crear un nuevo registro.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idcomentariosycalificaciones;

    /**
     * Texto del comentario realizado por el usuario.
     * No puede ser nulo y tiene una longitud máxima de 255 caracteres.
     */
    @Column(nullable = false, length = 255)
    private String comentario;

    /**
     * Fecha en que se registró el comentario en el sistema.
     * Se almacena como un timestamp para incluir la hora exacta.
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha_registro;

    /**
     * Fecha en que se realizó el comentario.
     * Puede diferir de la fecha de registro y se almacena como un timestamp.
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha_comentario;

    /**
     * Calificación numérica asignada al emprendimiento.
     * No puede ser nula y generalmente representa una escala (por ejemplo, de 1 a 5).
     */
    @Column(nullable = false)
    private Integer calificacion;

    /**
     * Identificador del emprendimiento al que se refiere el comentario.
     * No puede ser nulo y debe corresponder a un ID válido en la tabla de emprendimientos.
     */
    @Column(nullable = false)
    private int idemprendimiento;

    /**
     * Identificador del usuario que realizó el comentario.
     * No puede ser nulo y debe corresponder a un ID válido en la tabla de usuarios.
     */
    @Column(nullable = false)
    private int idusuarios;
}

