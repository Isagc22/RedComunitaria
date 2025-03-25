package com.example.proyectotalentotech.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "tipodocumento")
@Data
public class TipoDocumento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idtipodocumento;

    @Column(nullable = false, length = 100)
    private String nombre_tipo_documento;


}

