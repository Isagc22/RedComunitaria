package com.example.proyectotalentotech.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Entity
@Table(name = "comentariosycalificaciones")
@Data
public class ComentariosYCalificaciones {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idcomentariosycalificaciones;

    @Column(nullable = false, length = 255)
    private String comentario;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha_registro;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha_comentario;

    @Column(nullable = false)
    private Integer calificacion;

    @Column(nullable = false)
    private int idemprendimiento;

    @Column(nullable = false)
    private int idusuarios;







}

