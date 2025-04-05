package com.example.proyectotalentotech.controller;

import com.example.proyectotalentotech.model.HistorialEmprendimiento;
import com.example.proyectotalentotech.services.EmprendimientoService;
import com.example.proyectotalentotech.services.HistorialEmprendimientoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.HashMap;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/historialEmprendimiento")
public class HistorialEmprendimientoController {

    private final HistorialEmprendimientoService historialEmprendimientoService;
    private final EmprendimientoService emprendimientoService;

    public HistorialEmprendimientoController(HistorialEmprendimientoService historialEmprendimientoService, EmprendimientoService emprendimientoService) {
        this.historialEmprendimientoService = historialEmprendimientoService;
        this.emprendimientoService = emprendimientoService;
    }

    @PostMapping
    public ResponseEntity<HistorialEmprendimiento> crear(@RequestBody HistorialEmprendimiento historialEmprendimiento) {
        if (!emprendimientoService.obtenerPorId(historialEmprendimiento.getIdemprendimiento()).isPresent()){
            System.out.println("Emprendimiento con id: " + historialEmprendimiento.getIdemprendimiento()+ " no existe");
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(historialEmprendimientoService.guardar(historialEmprendimiento));
    }


    @GetMapping
    public ResponseEntity<List<HistorialEmprendimiento>> listarTodos() {
        List<HistorialEmprendimiento> lista = historialEmprendimientoService.listarTodos();
        if (lista.isEmpty()){
            System.out.println("⚠️ La tabla de historial de emprendimientos está vacía.");
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistorialEmprendimiento> obtenerPorId(@PathVariable Integer id) {
        if(historialEmprendimientoService.obtenerPorId(id).isEmpty()){
            System.out.println("No se encontró el historial de emprendimiento con id " + id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(historialEmprendimientoService.obtenerPorId(id).get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<HistorialEmprendimiento> actualizar(@PathVariable Integer id, @RequestBody HistorialEmprendimiento historialEmprendimiento) {
        Optional <HistorialEmprendimiento> entity = historialEmprendimientoService.editarPorId(id);

        if (entity.isPresent()) {
            HistorialEmprendimiento historialEmprendimientoActual = entity.get();
            historialEmprendimientoActual.setPais(historialEmprendimiento.getPais());
            historialEmprendimientoActual.setCantidad_emprendimiento(historialEmprendimiento.getCantidad_emprendimiento());
            historialEmprendimientoActual.setYear(historialEmprendimiento.getYear());
            historialEmprendimientoActual.setIdemprendimiento(historialEmprendimiento.getIdemprendimiento());
            historialEmprendimientoActual.setCantidad_aportada(historialEmprendimiento.getCantidad_aportada());
            historialEmprendimientoActual.setFecha(historialEmprendimiento.getFecha());
            historialEmprendimientoActual.setPaso(historialEmprendimiento.getPaso());
            return ResponseEntity.ok(historialEmprendimientoService.guardar(historialEmprendimientoActual));
        } else {
            System.out.println("No se encontró el historial de emprendimiento con id " + id);
            return ResponseEntity.badRequest().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (!historialEmprendimientoService.obtenerPorId(id).isPresent()) {;
            System.out.println("No se encontró el emprendimiento con id " + id);
            return ResponseEntity.badRequest().build();
        }
        historialEmprendimientoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint para obtener top 10 países con mayor emprendimiento
    @GetMapping("/top-paises")
    public ResponseEntity<?> getTopPaisesEmprendimiento() {
        try {
            List<Map<String, Object>> resultados = historialEmprendimientoService.getTopPaisesEmprendimiento();
            return ResponseEntity.ok(resultados);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Error al obtener top países con mayor emprendimiento");
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}