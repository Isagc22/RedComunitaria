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

/**
 * Controlador REST para el panel de control (dashboard) de la aplicación.
 * <p>
 * Esta clase proporciona endpoints para obtener datos estadísticos y analíticos
 * que serán mostrados en el panel de control del frontend. Incluye información
 * sobre emprendimientos categorizados por tipo, permitiendo visualizar los datos
 * en gráficos y reportes.
 * </p>
 * 
 * @author Equipo RedComunitaria
 * @version 1.0
 * @since 2023-03-30
 */
@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = {"http://localhost:3000"}, 
             methods = {RequestMethod.GET, RequestMethod.POST},
             allowedHeaders = "*")
public class DashboardController {

    private final EmprendimientoService emprendimientoService;

    /**
     * Constructor para la inyección de dependencias.
     * 
     * @param emprendimientoService Servicio para gestionar operaciones de emprendimientos
     */
    @Autowired
    public DashboardController(EmprendimientoService emprendimientoService) {
        this.emprendimientoService = emprendimientoService;
    }

    /**
     * Proporciona datos sobre emprendimientos clasificados por tipo para visualización en gráficos.
     * <p>
     * Este endpoint recupera todos los emprendimientos del sistema y los agrupa por tipo,
     * contando cuántos hay en cada categoría. Si no hay datos disponibles, devuelve datos
     * de ejemplo para que el frontend pueda mostrar una vista previa.
     * </p>
     * 
     * @return ResponseEntity con un mapa que contiene las etiquetas (tipos), datos (conteo)
     *         y total de emprendimientos
     */
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