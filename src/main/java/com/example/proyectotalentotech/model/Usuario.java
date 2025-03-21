package com.example.proyectotalentotech.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "USUARIOS")
@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUSUARIOS;

    @Column(nullable = false, unique = true, length = 255)
    private String email_user;

    @Column(nullable = false, length = 255)
    private String password_user;

    private Boolean estado_user;



    @OneToMany(mappedBy = "usuario")
    private List<Roles> roles;

    @OneToOne(mappedBy = "usuario")
    private DatosPersonales datosPersonales;

}

