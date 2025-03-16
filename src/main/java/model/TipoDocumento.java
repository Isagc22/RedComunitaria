package model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "TIPODOCUMENTO")
@Data
public class TipoDocumento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTIPODOCUMENTO;

    @Column(nullable = false, length = 100)
    private String nombre_tipo_documento;

    @OneToMany(mappedBy = "tipoDocumento")
    private List<DatosPersonales> datosPersonales;

}

