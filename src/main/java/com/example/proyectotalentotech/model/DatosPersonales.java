package com.example.proyectotalentotech.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "datospersonales")
@Data
public class DatosPersonales {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer iddatospersonales;

    @Column(nullable = false, length = 255)
    private String nombre_completo;

    @Column(nullable = false, unique = true)
    private String cedula;

    @Column(nullable = false, length = 255)
    private String direccion;

    @Column(nullable = false, length = 15)
    private String telefono;

    @Column(name = "imagen")
    private byte[] imagen;

    @Column(nullable = false)
    private int idusuarios;

    @Column(nullable = false)
    private int idtipodocumento;

}

