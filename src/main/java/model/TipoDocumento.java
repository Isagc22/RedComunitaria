package model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "tipodocumento")
public class TipoDocumento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTipoDocumento;

    @Column(nullable = false, length = 100)
    private String nombreTipoDocumento;

    @OneToMany(mappedBy = "tipoDocumento")
    private List<DatosPersonales> datosPersonales;

    // Getters y Setters
}

