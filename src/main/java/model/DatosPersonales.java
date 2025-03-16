package model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Entity
@Table(name = "datospersonales")
public class DatosPersonales {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDatosPersonales;

    @Column(nullable = false)
    private Date fechaNacimiento;

    @Column(nullable = false, length = 255)
    private String direccion;

    @Column(nullable = false, length = 15)
    private String telefono;

    @ManyToOne
    @JoinColumn(name = "idTipoDocumento", nullable = false)
    private TipoDocumento tipoDocumento;

    @OneToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    private Usuario usuario;

    // Getters y Setters
}

