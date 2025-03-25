package com.example.proyectotalentotech.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "tipousuario")
@Data
public class TipoUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idtipousuario;

    @Column(nullable = false, length = 100)
    private String nombre_tipo_usuario;

    @Column(nullable = false)
    private boolean estado_tipo_usuario;





}

