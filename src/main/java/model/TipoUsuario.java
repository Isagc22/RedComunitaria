
package model;

import jakarta.persistence.*;
import java.util.List;


@Entity
@Table(name = "tipousuario")
public class TipoUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTipoUsuario;

    @Column(nullable = false, length = 100)
    private String nombre;

    @OneToMany(mappedBy = "tipoUsuario")
    private List<Usuario> usuarios;

    // Getters y Setters
}

