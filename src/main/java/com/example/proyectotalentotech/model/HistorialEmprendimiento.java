package com.example.proyectotalentotech.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Entity
@Table(name = "HISTORIAL_EMPRENDIMIENTO")
@Data
public class HistorialEmprendimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idHistorialEmprendimiento;

    @Column(nullable = false, length = 255)
    private String paso;

    @Column(nullable = false, length = 255)
    private String cantidadAportada;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    @ManyToOne
    @JoinColumn(name = "idEmprendimiento", nullable = false)
    private Emprendimiento emprendimiento;
}
