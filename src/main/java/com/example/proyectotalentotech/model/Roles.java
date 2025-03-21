package com.example.proyectotalentotech.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "ROLES")
@Data
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idROLES;

    @Column(nullable = false)
    private LocalDateTime creado;

    @Column(nullable = false)
    private LocalDateTime modificado;

    @ManyToOne
    @JoinColumn(name = "idUSUARIOS", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "idTIPOUSUARIO", nullable = false)
    private TipoUsuario tipoUsuario;

}

