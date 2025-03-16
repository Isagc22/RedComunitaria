package model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "PRODUCCION_CONSUMO_ENERGIA")
public class ProduccionConsumoEnergia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProduccionConsumoEnergia;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal produccionEnergia;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal consumoEnergia;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;

    @ManyToOne
    @JoinColumn(name = "idEmprendimiento", nullable = false)
    private Emprendimiento emprendimiento;
}
