package com.example.proyectotalentotech.controller;

import com.example.proyectotalentotech.model.Emprendimiento;
import com.example.proyectotalentotech.services.EmprendimientoService;
import com.example.proyectotalentotech.services.HistorialEmprendimientoService;
import com.example.proyectotalentotech.services.ProduccionConsumoEnergiaServices;
import com.example.proyectotalentotech.services.RegionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.*;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = {"http://localhost:3000"}, 
             methods = {RequestMethod.GET, RequestMethod.POST},
             allowedHeaders = "*")
public class DashboardController {

    private final EmprendimientoService emprendimientoService;

    @Autowired
    public DashboardController(EmprendimientoService emprendimientoService) {
        this.emprendimientoService = emprendimientoService;
    }

    // Endpoint para datos simplificados - Emprendimientos existentes
    @GetMapping("/emprendimientos-datos")
    public ResponseEntity<Map<String, Object>> getDatosEmprendimientos() {
        try {
            // Obtener todos los emprendimientos
            List<Emprendimiento> emprendimientos = emprendimientoService.listarTodos();
            
            if (emprendimientos.isEmpty()) {
                // Si no hay datos, devolver datos de ejemplo
                Map<String, Object> datosPrueba = new HashMap<>();
                datosPrueba.put("labels", Arrays.asList("Tecnología", "Agricultura", "Servicios", "Manufactura", "Otro"));
                datosPrueba.put("datos", Arrays.asList(3, 5, 2, 1, 4));
                datosPrueba.put("mensaje", "Mostrando datos de ejemplo. No se encontraron emprendimientos en la base de datos.");
                return ResponseEntity.ok(datosPrueba);
            }
            
            // Contar emprendimientos por tipo
            Map<String, Integer> contadorPorTipo = new HashMap<>();
            
            for (Emprendimiento emp : emprendimientos) {
                String tipo = emp.getTipo();
                if (tipo == null || tipo.isEmpty()) {
                    tipo = "No especificado";
                }
                contadorPorTipo.put(tipo, contadorPorTipo.getOrDefault(tipo, 0) + 1);
            }
            
            // Preparar datos para gráfico
            List<String> labels = new ArrayList<>(contadorPorTipo.keySet());
            List<Integer> datos = new ArrayList<>();
            
            for (String tipo : labels) {
                datos.add(contadorPorTipo.get(tipo));
            }
            
            Map<String, Object> resultado = new HashMap<>();
            resultado.put("labels", labels);
            resultado.put("datos", datos);
            resultado.put("total", emprendimientos.size());
            
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            e.printStackTrace(); // Loggear el error completo
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Error al obtener datos de emprendimientos: " + e.getMessage());
            error.put("labels", Arrays.asList("Tecnología", "Agricultura", "Servicios", "Manufactura", "Otro"));
            error.put("datos", Arrays.asList(3, 5, 2, 1, 4));
            return ResponseEntity.ok(error); // Devolvemos 200 con datos de ejemplo en caso de error
        }
    }
} 