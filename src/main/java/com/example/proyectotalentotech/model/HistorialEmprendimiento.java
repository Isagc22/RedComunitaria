package com.example.proyectotalentotech.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Entity
@Table(name = "historialemprendimiento")
@Data
public class HistorialEmprendimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idhistorialemprendimiento;

    @Column(nullable = false)
    private String pais;

    @Column(nullable = false)
    private String cantidad_emprendimiento;

    @Column(nullable = false)
    private String year;

    @Column(nullable = false)
    private int idemprendimiento;

    @Column(nullable = false, length = 255)
    private String cantidad_aportada;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    @Column(nullable = false, length = 255)
    private String paso;

}
