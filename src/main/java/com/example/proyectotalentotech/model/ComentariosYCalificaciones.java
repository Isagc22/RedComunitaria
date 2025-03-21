package com.example.proyectotalentotech.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Entity
@Table(name = "COMENTARIOS_Y_CALIFICACIONES")
@Data
public class ComentariosYCalificaciones {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idComentariosCalificaciones;

    @Column(nullable = false, length = 255)
    private String comentarios;

    @Column(nullable = false)
    private Integer calificacion;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;

    @ManyToOne
    @JoinColumn(name = "idEmprendimiento", nullable = false)
    private Emprendimiento emprendimiento;

    //gfhfgh
    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    private Usuario usuario;

}

