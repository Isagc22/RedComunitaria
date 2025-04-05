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

/**
 * Controlador REST para la gestión del historial de emprendimientos.
 * <p>
 * Esta clase proporciona endpoints para crear, leer, actualizar y eliminar
 * registros del historial de emprendimientos, así como funcionalidades específicas
 * como la obtención de estadísticas sobre los países con mayor cantidad de emprendimientos.
 * </p>
 * 
 * @author Equipo RedComunitaria
 * @version 1.0
 * @since 2023-03-30
 */
@RestController
@RequestMapping("/historialEmprendimiento")
public class HistorialEmprendimientoController {

    private final HistorialEmprendimientoService historialEmprendimientoService;
    private final EmprendimientoService emprendimientoService;

    /**
     * Constructor para la inyección de dependencias.
     * 
     * @param historialEmprendimientoService Servicio para la gestión del historial de emprendimientos
     * @param emprendimientoService Servicio para gestionar operaciones de emprendimientos
     */
    public HistorialEmprendimientoController(HistorialEmprendimientoService historialEmprendimientoService, EmprendimientoService emprendimientoService) {
        this.historialEmprendimientoService = historialEmprendimientoService;
        this.emprendimientoService = emprendimientoService;
    }

    /**
     * Crea un nuevo registro en el historial de emprendimientos.
     * <p>
     * Verifica que el emprendimiento asociado exista antes de crear el registro.
     * </p>
     * 
     * @param historialEmprendimiento El objeto HistorialEmprendimiento a crear
     * @return ResponseEntity con el historial creado o un error si las validaciones fallan
     */
    @PostMapping
    public ResponseEntity<HistorialEmprendimiento> crear(@RequestBody HistorialEmprendimiento historialEmprendimiento) {
        if (!emprendimientoService.obtenerPorId(historialEmprendimiento.getIdemprendimiento()).isPresent()){
            System.out.println("Emprendimiento con id: " + historialEmprendimiento.getIdemprendimiento()+ " no existe");
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(historialEmprendimientoService.guardar(historialEmprendimiento));
    }

    /**
     * Obtiene la lista de todos los registros del historial de emprendimientos.
     * 
     * @return ResponseEntity con la lista de registros o un error si la lista está vacía
     */
    @GetMapping
    public ResponseEntity<List<HistorialEmprendimiento>> listarTodos() {
        List<HistorialEmprendimiento> lista = historialEmprendimientoService.listarTodos();
        if (lista.isEmpty()){
            System.out.println("⚠️ La tabla de historial de emprendimientos está vacía.");
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(lista);
    }

    /**
     * Busca un registro del historial por su identificador único.
     * 
     * @param id El identificador del registro a buscar
     * @return ResponseEntity con el registro si se encuentra, o notFound si no existe
     */
    @GetMapping("/{id}")
    public ResponseEntity<HistorialEmprendimiento> obtenerPorId(@PathVariable Integer id) {
        if(historialEmprendimientoService.obtenerPorId(id).isEmpty()){
            System.out.println("No se encontró el historial de emprendimiento con id " + id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(historialEmprendimientoService.obtenerPorId(id).get());
    }

    /**
     * Actualiza un registro del historial existente.
     * <p>
     * Permite actualizar todos los campos del registro, incluyendo país, cantidad, año,
     * emprendimiento asociado, cantidad aportada, fecha y paso.
     * </p>
     * 
     * @param id El identificador del registro a actualizar
     * @param historialEmprendimiento El objeto HistorialEmprendimiento con los nuevos datos
     * @return ResponseEntity con el registro actualizado, o badRequest si no existe
     */
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

    /**
     * Elimina un registro del historial por su identificador único.
     * 
     * @param id El identificador del registro a eliminar
     * @return ResponseEntity sin contenido (204) si la eliminación fue exitosa, o badRequest si no existe
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (!historialEmprendimientoService.obtenerPorId(id).isPresent()) {;
            System.out.println("No se encontró el emprendimiento con id " + id);
            return ResponseEntity.badRequest().build();
        }
        historialEmprendimientoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Obtiene los 10 países con mayor cantidad de emprendimientos.
     * <p>
     * Este endpoint proporciona datos estadísticos sobre la distribución global
     * de emprendimientos, identificando los países con mayor concentración.
     * </p>
     * 
     * @return ResponseEntity con la lista de países y sus cantidades de emprendimiento
     */
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