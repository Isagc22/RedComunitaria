package com.example.proyectotalentotech.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entidad que representa los registros de producción y consumo de energía de los emprendimientos.
 * <p>
 * Esta clase mapea a la tabla 'produccionconsumoenergia' en la base de datos y almacena
 * información sobre la energía producida y consumida por los emprendimientos en fechas específicas,
 * así como detalles sobre la fuente de energía y observaciones relacionadas.
 * </p>
 * 
 * @author Equipo RedComunitaria
 * @version 1.0
 * @since 2023-03-30
 */
@Entity
@Table(name = "produccionconsumoenergia")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProduccionConsumoEnergia {
    /**
     * Identificador único del registro de producción y consumo.
     * Se genera automáticamente al crear un nuevo registro.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idproduccionconsumoenergia")
    private Long id;
    
    /**
     * Identificador del emprendimiento al que pertenecen estos datos.
     * No puede ser nulo y debe corresponder a un ID válido en la tabla de emprendimientos.
     */
    @Column(name = "idemprendimiento", nullable = false)
    private Long emprendimientoId;
    
    /**
     * Fecha en que se registraron los datos de producción y consumo.
     * Se formatea en el patrón "yyyy-MM-dd".
     */
    @Column(name = "fecha")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha;
    
    /**
     * Cantidad de energía producida por el emprendimiento.
     * No puede ser nulo y se expresa generalmente en kilovatios-hora (kWh).
     */
    @Column(name = "produccion_energia", nullable = false)
    private Double energiaProducida;
    
    /**
     * Cantidad de energía consumida por el emprendimiento.
     * No puede ser nulo y se expresa generalmente en kilovatios-hora (kWh).
     */
    @Column(name = "consumo_energia", nullable = false)
    private Double energiaConsumida;
    
    /**
     * Tipo o fuente de energía utilizada o producida.
     * Puede ser solar, eólica, hidráulica, biomasa, etc.
     */
    @Column(name = "fuente_energia")
    private String fuenteEnergia;
    
    /**
     * Notas adicionales o información relevante sobre los datos registrados.
     * Se almacena como un campo de texto que puede contener descripciones extensas.
     */
    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;
    
    /**
     * Usuario que realizó el registro de los datos.
     * Almacena el identificador o nombre del usuario para auditoría.
     */
    @Column(name = "usuario_registro")
    private String usuarioRegistro;
    
    /**
     * Fecha y hora exactas en que se realizó el registro en el sistema.
     * Se formatea en el patrón "yyyy-MM-dd HH:mm:ss".
     */
    @Column(name = "fecha_registro")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaRegistro;
    
    /**
     * Relación con la entidad Emprendimiento.
     * Permite acceder a los datos completos del emprendimiento desde este registro.
     * Es una relación de muchos a uno y se carga de forma perezosa (LAZY).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idemprendimiento", referencedColumnName = "idemprendimiento", insertable = false, updatable = false)
    private Emprendimiento emprendimiento;
    
    /**
     * Método ejecutado automáticamente antes de persistir un nuevo registro.
     * Establece la fecha de registro como la fecha y hora actuales del sistema.
     */
    @PrePersist
    protected void onCreate() {
        fechaRegistro = LocalDateTime.now();
    }
}
