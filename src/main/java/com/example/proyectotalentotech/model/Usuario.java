package com.example.proyectotalentotech.model;
import jakarta.persistence.*;
import lombok.Data;
@Entity
@Table(name = "usuarios")
@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idusuarios;

    @Column(name = "email_user", nullable = false, unique = true, length = 255)
    private String emailUser;

    @Column(nullable = false, length = 255)
    private String password_user;

    @Column(nullable = false)
    private Boolean estado_user;
}

