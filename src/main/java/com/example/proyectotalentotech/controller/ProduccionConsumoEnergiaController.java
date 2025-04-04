package com.example.proyectotalentotech.controller;

import com.example.proyectotalentotech.model.ProduccionConsumoEnergia;
import com.example.proyectotalentotech.model.dto.ProduccionConsumoDto;
import com.example.proyectotalentotech.services.ProduccionConsumoEnergiaServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@RestController
@RequestMapping("/api/produccion-consumo")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
public class ProduccionConsumoEnergiaController {

    private final ProduccionConsumoEnergiaServices produccionConsumoEnergiaServices;

    @Autowired
    public ProduccionConsumoEnergiaController(ProduccionConsumoEnergiaServices produccionConsumoEnergiaServices) {
        this.produccionConsumoEnergiaServices = produccionConsumoEnergiaServices;
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarDatos(@RequestBody ProduccionConsumoDto dto, Authentication authentication) {
        try {
            System.out.println("Recibiendo datos para registrar: " + dto);
            
            ProduccionConsumoEnergia entidad = new ProduccionConsumoEnergia();
            entidad.setEmprendimientoId(dto.getEmprendimientoId());
            entidad.setFecha(LocalDate.parse(dto.getFecha()));
            entidad.setEnergiaProducida(dto.getEnergiaProducida());
            entidad.setEnergiaConsumida(dto.getEnergiaConsumida());
            entidad.setFuenteEnergia(dto.getFuenteEnergia());
            entidad.setObservaciones(dto.getObservaciones());
            
            // Si hay autenticación, usar el nombre del usuario, de lo contrario usar un valor por defecto
            if (authentication != null) {
                entidad.setUsuarioRegistro(authentication.getName());
            } else {
                entidad.setUsuarioRegistro("usuario_default");
            }
            
            // Establecer la fecha de registro
            entidad.setFechaRegistro(LocalDateTime.now());
            
            ProduccionConsumoEnergia guardado = produccionConsumoEnergiaServices.guardar(entidad);
            
            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Datos registrados correctamente");
            response.put("id", guardado.getId());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Error al registrar datos: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/datos/{emprendimientoId}")
    public ResponseEntity<?> obtenerDatosPorEmprendimiento(
            @PathVariable Long emprendimientoId,
            @RequestParam(defaultValue = "mes") String filtro) {
        
        try {
            // Obtener fecha límite según filtro
            LocalDate fechaHasta = LocalDate.now();
            LocalDate fechaDesde;
            
            switch (filtro) {
                case "semana":
                    fechaDesde = fechaHasta.minus(7, ChronoUnit.DAYS);
                    break;
                case "trimestre":
                    fechaDesde = fechaHasta.minus(3, ChronoUnit.MONTHS);
                    break;
                case "anio":
                    fechaDesde = fechaHasta.minus(1, ChronoUnit.YEARS);
                    break;
                case "mes":
                default:
                    fechaDesde = fechaHasta.minus(1, ChronoUnit.MONTHS);
                    break;
            }
            
            // Obtener datos según rango de fechas
            List<ProduccionConsumoEnergia> datos = produccionConsumoEnergiaServices
                    .obtenerPorEmprendimientoYRangoFechas(emprendimientoId, fechaDesde, fechaHasta);
            
            if (datos.isEmpty()) {
                // Devolver datos de ejemplo si no hay datos
                return ResponseEntity.ok(generarDatosEjemplo(filtro));
            }
            
            // Formatear fechas según filtro
            DateTimeFormatter formatter;
            if ("anio".equals(filtro)) {
                formatter = DateTimeFormatter.ofPattern("MMM yyyy");
            } else {
                formatter = DateTimeFormatter.ofPattern("dd/MM");
            }
            
            // Preparar datos para gráficos
            List<String> fechas = new ArrayList<>();
            List<Double> energiaProducida = new ArrayList<>();
            List<Double> energiaConsumida = new ArrayList<>();
            
            for (ProduccionConsumoEnergia dato : datos) {
                fechas.add(dato.getFecha().format(formatter));
                energiaProducida.add(dato.getEnergiaProducida());
                energiaConsumida.add(dato.getEnergiaConsumida());
            }
            
            Map<String, Object> resultado = new HashMap<>();
            resultado.put("fechas", fechas);
            resultado.put("energiaProducida", energiaProducida);
            resultado.put("energiaConsumida", energiaConsumida);
            resultado.put("emprendimientoId", emprendimientoId);
            resultado.put("periodoConsultado", filtro);
            
            return ResponseEntity.ok(resultado);
            
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Error al obtener datos: " + e.getMessage());
            return ResponseEntity.ok(generarDatosEjemplo(filtro)); // Devolver datos de ejemplo en caso de error
        }
    }
    
    // Método para generar datos de ejemplo
    private Map<String, Object> generarDatosEjemplo(String filtro) {
        List<String> fechas = new ArrayList<>();
        List<Double> produccion = new ArrayList<>();
        List<Double> consumo = new ArrayList<>();
        
        if ("semana".equals(filtro)) {
            fechas = Arrays.asList("Lun", "Mar", "Mié", "Jue", "Vie", "Sáb", "Dom");
            produccion = Arrays.asList(25.4, 30.2, 28.6, 32.1, 29.5, 35.8, 33.2);
            consumo = Arrays.asList(42.1, 38.5, 45.2, 37.8, 40.3, 36.9, 39.4);
        } else if ("anio".equals(filtro)) {
            fechas = Arrays.asList("Ene 2023", "Feb 2023", "Mar 2023", "Abr 2023", "May 2023", "Jun 2023", 
                                  "Jul 2023", "Ago 2023", "Sep 2023", "Oct 2023", "Nov 2023", "Dic 2023");
            produccion = Arrays.asList(210.5, 195.3, 230.8, 245.6, 280.2, 310.5, 320.1, 305.7, 290.2, 260.8, 240.3, 225.6);
            consumo = Arrays.asList(320.5, 310.2, 350.7, 330.5, 365.8, 380.2, 390.5, 385.2, 360.8, 340.5, 325.2, 330.8);
        } else {
            // Datos para mes o trimestre
            fechas = Arrays.asList("01/01", "05/01", "10/01", "15/01", "20/01", "25/01", "30/01");
            produccion = Arrays.asList(85.2, 92.5, 78.6, 95.3, 88.7, 90.2, 93.8);
            consumo = Arrays.asList(120.3, 115.8, 125.2, 118.7, 130.5, 122.8, 128.3);
        }
        
        Map<String, Object> resultado = new HashMap<>();
        resultado.put("fechas", fechas);
        resultado.put("energiaProducida", produccion);
        resultado.put("energiaConsumida", consumo);
        resultado.put("ejemplo", true);
        resultado.put("mensaje", "Mostrando datos de ejemplo. No se encontraron registros para el período seleccionado.");
        
        return resultado;
    }
}