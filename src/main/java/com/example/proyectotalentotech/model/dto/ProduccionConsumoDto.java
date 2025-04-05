package com.example.proyectotalentotech.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProduccionConsumoDto {
    private Long emprendimientoId;
    private String fecha;
    private Double energiaProducida;
    private Double energiaConsumida;
    private String fuenteEnergia;
    private String observaciones;
} 