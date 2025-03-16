package model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "roles")
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRoles;

    @Column(nullable = false)
    private LocalDateTime creado;

    @Column(nullable = false)
    private LocalDateTime modificado;

    @ManyToOne
    @JoinColumn(name = "idUsuarios", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "idTipoUsuario", nullable = false)
    private TipoUsuario tipoUsuario;
}

