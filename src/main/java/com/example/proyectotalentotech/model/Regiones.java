package com.example.proyectotalentotech.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

/**
 * Entidad que representa las regiones geográficas en el sistema.
 * <p>
 * Esta clase mapea a la tabla 'regiones' en la base de datos y almacena
 * información sobre las diferentes regiones geográficas donde pueden
 * ubicarse los emprendimientos.
 * </p>
 * 
 * @author Equipo RedComunitaria
 * @version 1.0
 * @since 2023-03-30
 */
@Entity
@Table(name = "regiones")
@Data
public class Regiones {
    /**
     * Identificador único de la región.
     * Se genera automáticamente al crear un nuevo registro.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idregiones;

    /**
     * Nombre de la región geográfica.
     * No puede ser nulo y tiene una longitud máxima de 100 caracteres.
     * Ejemplos: "Bogotá", "Antioquia", "Valle del Cauca".
     */
    @Column(nullable = false, length = 100)
    private String nombre_region;

}
