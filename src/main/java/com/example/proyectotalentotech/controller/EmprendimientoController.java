package com.example.proyectotalentotech.controller;

import com.example.proyectotalentotech.model.Emprendimiento;
import com.example.proyectotalentotech.services.EmprendimientoService;
import com.example.proyectotalentotech.services.RegionesService;
import com.example.proyectotalentotech.services.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/emprendimientos")
public class EmprendimientoController {

    private final EmprendimientoService emprendimientoService;
    private final RegionesService regionesService;
    private final UsuarioService usuarioService;

    public EmprendimientoController(EmprendimientoService emprendimientoService, RegionesService regionesService, UsuarioService usuarioService) {

        this.emprendimientoService = emprendimientoService;
        this.regionesService = regionesService;
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<Emprendimiento> crear(@RequestBody Emprendimiento emprendimiento) {
        if (!regionesService.obtenerPorId(emprendimiento.getIdregiones()).isPresent()){
            System.out.println("Región con id " + emprendimiento.getIdregiones() + " no existe");
            return ResponseEntity.badRequest().body(null);
        }

        if (!usuarioService.obtenerPorId(emprendimiento.getIdusuarios()).isPresent()) {
            System.out.println("Usuario con id " + emprendimiento.getIdusuarios() + " no existe");
            return ResponseEntity.badRequest().body(null);
        }


        return ResponseEntity.ok(emprendimientoService.guardar(emprendimiento));
    }

    @GetMapping
    public ResponseEntity<List<Emprendimiento>> listarTodos() {
        List<Emprendimiento> lista = emprendimientoService.listarTodos();

        if (lista.isEmpty()) {
            System.out.println("⚠️ La tabla de emprendimientos está vacía.");
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(emprendimientoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Emprendimiento> obtenerPorId(@PathVariable Integer id) {
        Optional<Emprendimiento> entity = emprendimientoService.obtenerPorId(id);

        if (entity.isEmpty()){
            System.out.println("No se encontró el emprendimiento con id " + id);
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(entity.get());
    }

    @PutMapping("/{id}")
    public  ResponseEntity<Emprendimiento> editarPorId(@PathVariable Integer id, @RequestBody Emprendimiento emprendimiento) {
        Optional<Emprendimiento> entity = emprendimientoService.editarPorId(id);

        if (entity.isPresent()){
            Emprendimiento emprendimientoActual = entity.get();
            emprendimientoActual.setNombre(emprendimiento.getNombre());
            emprendimientoActual.setDescripcion(emprendimiento.getDescripcion());
            emprendimientoActual.setTipo(emprendimiento.getTipo());
            emprendimientoActual.setFecha_creacion(emprendimiento.getFecha_creacion());
            emprendimientoActual.setEstado_emprendimiento(emprendimiento.getEstado_emprendimiento());
            emprendimientoActual.setImagen_emprendimiento(emprendimiento.getImagen_emprendimiento());
            emprendimientoActual.setIdregiones(emprendimiento.getIdregiones());
            emprendimientoActual.setIdusuarios(emprendimiento.getIdusuarios());
            return ResponseEntity.ok(emprendimientoService.guardar(emprendimientoActual));
        } else {
            System.out.println("No se encontró el emprendimiento con id " + id);
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (!emprendimientoService.obtenerPorId(id).isPresent()) {;
            System.out.println("No se encontró el emprendimiento con id " + id);
            return ResponseEntity.badRequest().build();
        }
        emprendimientoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
