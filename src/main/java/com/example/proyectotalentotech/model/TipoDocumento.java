package com.example.proyectotalentotech.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

/**
 * Entidad que representa los tipos de documento de identidad en el sistema.
 * <p>
 * Esta clase mapea a la tabla 'tipodocumento' en la base de datos y almacena
 * información sobre los diferentes tipos de documentos de identidad reconocidos
 * por el sistema, como cédula de ciudadanía, pasaporte, etc.
 * </p>
 * 
 * @author Equipo RedComunitaria
 * @version 1.0
 * @since 2023-03-30
 */
@Entity
@Table(name = "tipodocumento")
@Data
public class TipoDocumento {
    /**
     * Identificador único del tipo de documento.
     * Se genera automáticamente al crear un nuevo registro.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idtipodocumento;

    /**
     * Nombre del tipo de documento.
     * No puede ser nulo y tiene una longitud máxima de 100 caracteres.
     * Ejemplos: "Cédula de Ciudadanía", "Pasaporte", "Tarjeta de Identidad".
     */
    @Column(nullable = false, length = 100)
    private String nombre_tipo_documento;


}

