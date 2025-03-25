package com.example.proyectotalentotech.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "usuarios")
@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idusuarios;

    @Column(nullable = false, unique = true, length = 255)
    private String email_user;

    @Column(nullable = false, length = 255)
    private String password_user;


    @Column(nullable = false)
    private Boolean estado_user;

}

