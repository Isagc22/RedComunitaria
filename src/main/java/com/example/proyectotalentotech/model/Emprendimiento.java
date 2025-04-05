package com.example.proyectotalentotech.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

/**
 * Entidad que representa los emprendimientos registrados en el sistema.
 * <p>
 * Esta clase mapea a la tabla 'emprendimiento' en la base de datos y almacena
 * información sobre los diferentes emprendimientos creados por los usuarios,
 * incluyendo su nombre, descripción, tipo, región, estado, etc.
 * </p>
 * 
 * @author Equipo RedComunitaria
 * @version 1.0
 * @since 2023-03-30
 */
@Entity
@Table(name = "emprendimiento")
@Data
public class Emprendimiento {
    /**
     * Identificador único del emprendimiento.
     * Se genera automáticamente al crear un nuevo registro.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idemprendimiento;

    /**
     * Nombre del emprendimiento.
     * No puede ser nulo y tiene una longitud máxima de 100 caracteres.
     */
    @Column(nullable = false, length = 100)
    private String nombre;

    /**
     * Descripción detallada del emprendimiento.
     * No puede ser nula y contiene información sobre los productos o servicios ofrecidos.
     */
    @Column(nullable = false)
    private String descripcion;

    /**
     * Tipo o categoría del emprendimiento.
     * No puede ser nulo e indica la clasificación del emprendimiento (agrícola, artesanal, etc.).
     */
    @Column(nullable = false)
    private String tipo;

    /**
     * Fecha de creación del emprendimiento.
     * Almacena la fecha en que fue creado o registrado en el sistema.
     */
    @Temporal(TemporalType.DATE)
    private LocalDate fecha_creacion;

    /**
     * Estado del emprendimiento (activo/inactivo).
     * Indica si el emprendimiento está disponible para ser visualizado en el marketplace.
     */
    @Column(nullable = false)
    private Boolean estado_emprendimiento;

    /**
     * Imagen representativa del emprendimiento.
     * Almacena la imagen principal del emprendimiento como un array de bytes.
     */
    @Column(nullable = true, columnDefinition = "LONGBLOB")
    private byte[] imagen_emprendimiento;

    /**
     * Identificador de la región donde se ubica el emprendimiento.
     * No puede ser nulo y debe corresponder a un ID válido en la tabla de regiones.
     */
    @Column(nullable = false)
    private int idregiones;

    /**
     * Identificador del usuario propietario del emprendimiento.
     * No puede ser nulo y debe corresponder a un ID válido en la tabla de usuarios.
     */
    @Column(nullable = false)
    private Integer idusuarios;
}
