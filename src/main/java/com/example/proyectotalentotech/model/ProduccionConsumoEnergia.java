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

@Entity
@Table(name = "produccionconsumoenergia")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProduccionConsumoEnergia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idproduccionconsumoenergia")
    private Long id;
    
    @Column(name = "idemprendimiento", nullable = false)
    private Long emprendimientoId;
    
    @Column(name = "fecha")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha;
    
    @Column(name = "produccion_energia", nullable = false)
    private Double energiaProducida;
    
    @Column(name = "consumo_energia", nullable = false)
    private Double energiaConsumida;
    
    @Column(name = "fuente_energia")
    private String fuenteEnergia;
    
    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;
    
    @Column(name = "usuario_registro")
    private String usuarioRegistro;
    
    @Column(name = "fecha_registro")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaRegistro;
    
    // Relación con Emprendimiento (no obligatoria pero puede ser útil)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idemprendimiento", referencedColumnName = "idemprendimiento", insertable = false, updatable = false)
    private Emprendimiento emprendimiento;
    
    // Pre-persistencia para establecer fecha de registro
    @PrePersist
    protected void onCreate() {
        fechaRegistro = LocalDateTime.now();
    }
}
