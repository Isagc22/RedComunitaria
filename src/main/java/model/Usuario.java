package model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "USUARIOS")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUsuario;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    private Boolean estadoUser;

    // Relaciones
    @ManyToOne
    @JoinColumn(name = "tipoUsuario")
    private TipoUsuario tipoUsuario;

    @OneToOne(mappedBy = "usuario")
    private DatosPersonales datosPersonales;

    // Getters y Setters
}

