    package com.example.proyectotalentotech.model;

    import com.fasterxml.jackson.annotation.JsonBackReference;
    import com.fasterxml.jackson.annotation.JsonManagedReference;
    import jakarta.persistence.*;
    import lombok.Data;

    import java.math.BigDecimal;
    import java.util.Date;

    @Entity
    @Table(name = "produccionconsumoenergia")
    @Data
    public class ProduccionConsumoEnergia {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer idproduccionconsumoenergia;

        @Column(nullable = false, precision = 10, scale = 2)
        private BigDecimal produccion_energia;

        @Column(nullable = false, precision = 10, scale = 2)
        private BigDecimal consumo_energia;

        @Temporal(TemporalType.TIMESTAMP)
        private Date fecha_registro;

        @Column(nullable = false)
        private int idemprendimiento;
    }
