package com.example.proyectotalentotech.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Entity
@Table(name = "emprendimiento")
@Data
public class Emprendimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idemprendimiento;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private String tipo;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha_creacion;

    @Column(nullable = false)
    private Boolean estado_emprendimiento;

    @Column(nullable = true)
    private byte[] imagen_emprendimiento;

    @Column(nullable = false)
    private int idregiones;

    @Column(nullable = false)
    private int idusuarios;





}
