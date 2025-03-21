package com.example.proyectotalentotech.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Entity
@Table(name = "DATOSPERSONALES")
@Data
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

    @Column(name = "imagen", nullable = false)
    private byte[] imagen;

    @ManyToOne
    @JoinColumn(name = "idTipoDocumento", nullable = false)
    private TipoDocumento tipoDocumento;

    @OneToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    private Usuario usuario;


}

