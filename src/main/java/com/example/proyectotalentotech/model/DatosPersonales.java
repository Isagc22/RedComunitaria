package com.example.proyectotalentotech.model;
import jakarta.persistence.*;
import lombok.Data;

/**
 * Entidad que representa los datos personales de un usuario en el sistema.
 * <p>
 * Esta clase mapea a la tabla 'datospersonales' en la base de datos y almacena
 * información personal como nombre, cédula, dirección, teléfono e imagen de perfil.
 * Cada instancia está asociada a un usuario y a un tipo de documento.
 * </p>
 * 
 * @author Equipo RedComunitaria
 * @version 1.0
 * @since 2023-03-30
 */
@Entity
@Table(name = "datospersonales")
@Data
public class DatosPersonales {
    /**
     * Identificador único de los datos personales.
     * Se genera automáticamente al crear un nuevo registro.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer iddatospersonales;
    
    /**
     * Nombre completo del usuario.
     * No puede ser nulo y tiene una longitud máxima de 255 caracteres.
     */
    @Column(nullable = false, length = 255)
    private String nombre_completo;
    
    /**
     * Número de cédula o documento de identidad.
     * No puede ser nulo y debe ser único en el sistema.
     */
    @Column(nullable = false, unique = true)
    private String cedula;
    
    /**
     * Dirección física del usuario.
     * No puede ser nulo y tiene una longitud máxima de 255 caracteres.
     */
    @Column(nullable = false, length = 255)
    private String direccion;
    
    /**
     * Número telefónico de contacto.
     * No puede ser nulo y tiene una longitud máxima de 15 caracteres.
     */
    @Column(nullable = false, length = 15)
    private String telefono;
    
    /**
     * Imagen de perfil del usuario almacenada como un array de bytes.
     * Se guarda en la base de datos como un MEDIUMGBLOB.
     */
    @Column(name = "imagen", columnDefinition = "MEDIUMGBLOB")
    private byte[] imagen;
    
    /**
     * Identificador del usuario asociado a estos datos personales.
     * No puede ser nulo y debe corresponder a un ID válido en la tabla de usuarios.
     */
    @Column(nullable = false)
    private int idusuarios;
    
    /**
     * Identificador del tipo de documento.
     * No puede ser nulo y debe corresponder a un ID válido en la tabla de tipos de documento.
     */
    @Column(nullable = false)
    private int idtipodocumento;
}

