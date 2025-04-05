package com.example.proyectotalentotech.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

/**
 * Entidad que representa el historial de los emprendimientos en el sistema.
 * <p>
 * Esta clase mapea a la tabla 'historialemprendimiento' en la base de datos y almacena
 * información sobre el seguimiento histórico de los emprendimientos, incluyendo datos
 * sobre el país, cantidad, año, aportes y etapas del proceso de emprendimiento.
 * </p>
 * 
 * @author Equipo RedComunitaria
 * @version 1.0
 * @since 2023-03-30
 */
@Entity
@Table(name = "historialemprendimiento")
@Data
public class HistorialEmprendimiento {
    /**
     * Identificador único del registro de historial.
     * Se genera automáticamente al crear un nuevo registro.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idhistorialemprendimiento;

    /**
     * País donde se desarrolla el emprendimiento.
     * No puede ser nulo e indica la ubicación geográfica a nivel de país.
     */
    @Column(nullable = false)
    private String pais;

    /**
     * Cantidad de emprendimientos del mismo tipo o categoría.
     * No puede ser nulo y representa un conteo o medida cuantitativa.
     */
    @Column(nullable = false)
    private String cantidad_emprendimiento;

    /**
     * Año de referencia para el registro histórico.
     * No puede ser nulo e indica el período anual al que corresponde el registro.
     */
    @Column(nullable = false)
    private String year;

    /**
     * Identificador del emprendimiento asociado al historial.
     * No puede ser nulo y debe corresponder a un ID válido en la tabla de emprendimientos.
     */
    @Column(nullable = false)
    private int idemprendimiento;

    /**
     * Cantidad aportada o invertida en el emprendimiento.
     * No puede ser nulo, tiene una longitud máxima de 255 caracteres y representa un valor económico o de recursos.
     */
    @Column(nullable = false, length = 255)
    private String cantidad_aportada;

    /**
     * Fecha del registro histórico.
     * Almacena el momento exacto en que se realizó el registro, incluyendo hora.
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    /**
     * Etapa o paso en el que se encuentra el emprendimiento.
     * No puede ser nulo, tiene una longitud máxima de 255 caracteres y describe la fase actual del proceso.
     */
    @Column(nullable = false, length = 255)
    private String paso;
}
