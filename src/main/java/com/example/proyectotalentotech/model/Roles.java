package com.example.proyectotalentotech.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "roles")
@Data
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idroles;

    @Column(nullable = false)
    private LocalDateTime creado;

    @Column(nullable = false)
    private LocalDateTime modificado;

    @Column(nullable = false)
    private int idusuarios;

    @Column(nullable = false)
    private int idtipousuario;



}

