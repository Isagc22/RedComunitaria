package model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "TIPODOCUMENTO")
@Data
public class TipoDocumento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTIPODOCUMENTO;

    @Column(name = "nombre_tipo_documento")
    private String nombreTipoDocumento;
}
