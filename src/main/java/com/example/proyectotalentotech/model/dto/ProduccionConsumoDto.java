package com.example.proyectotalentotech.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Objeto de Transferencia de Datos (DTO) para manejar información de producción y consumo de energía.
 * <p>
 * Esta clase facilita la transferencia de datos de producción y consumo de energía entre
 * las capas de la aplicación, especialmente para comunicación con el frontend. No está
 * directamente mapeada a una tabla de la base de datos.
 * </p>
 * 
 * @author Equipo RedComunitaria
 * @version 1.0
 * @since 2023-03-30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProduccionConsumoDto {
    /**
     * Identificador del emprendimiento al que pertenecen los datos de producción y consumo.
     */
    private Long emprendimientoId;
    
    /**
     * Fecha en formato string en que se registraron los datos de producción y consumo.
     */
    private String fecha;
    
    /**
     * Cantidad de energía producida por el emprendimiento, medida en una unidad específica (kWh, etc.).
     */
    private Double energiaProducida;
    
    /**
     * Cantidad de energía consumida por el emprendimiento, medida en una unidad específica (kWh, etc.).
     */
    private Double energiaConsumida;
    
    /**
     * Tipo o fuente de energía utilizada o producida (solar, eólica, hidráulica, etc.).
     */
    private String fuenteEnergia;
    
    /**
     * Notas adicionales o información relevante sobre los datos de producción y consumo.
     */
    private String observaciones;
}