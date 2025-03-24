package com.example.proyectotalentotech.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "regiones")
@Data
public class Regiones {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idregiones;

    @Column(nullable = false, length = 100)
    private String nombre_region;

}
