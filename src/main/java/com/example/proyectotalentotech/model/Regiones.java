package com.example.proyectotalentotech.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "REGIONES")
@Data
public class Regiones {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRegion;

    @Column(nullable = false, length = 100)
    private String nombreRegion;

    @OneToMany(mappedBy = "region")
    private List<Emprendimiento> emprendimientos;

}
