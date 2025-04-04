package com.example.proyectotalentotech.controller;

import com.example.proyectotalentotech.model.Emprendimiento;
import com.example.proyectotalentotech.services.EmprendimientoService;
import com.example.proyectotalentotech.services.RegionesService;
import com.example.proyectotalentotech.services.UsuarioService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;



@RestController
@RequestMapping("/emprendimientos")
public class EmprendimientoController {

    private final EmprendimientoService emprendimientoService;
    private final RegionesService regionesService;
    private final UsuarioService usuarioService;

    public EmprendimientoController(EmprendimientoService emprendimientoService,
                                    RegionesService regionesService,
                                    UsuarioService usuarioService) {
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
        return ResponseEntity.ok(lista);
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

    @PostMapping(value = "/{id}/actualizar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Emprendimiento> editarPorId(
            @PathVariable Integer id,
            @ModelAttribute Emprendimiento emprendimiento,
            @RequestParam(value = "imagen", required = false) MultipartFile imagenFile) {

        Optional<Emprendimiento> entity = emprendimientoService.obtenerPorId(id);

        if (entity.isEmpty()) {
            System.out.println("No se encontró el emprendimiento con id " + id);
            return ResponseEntity.badRequest().build();
        }

        Emprendimiento emprendimientoActual = entity.get();
        emprendimientoActual.setNombre(emprendimiento.getNombre());
        emprendimientoActual.setDescripcion(emprendimiento.getDescripcion());
        emprendimientoActual.setTipo(emprendimiento.getTipo());
        emprendimientoActual.setFecha_creacion(emprendimiento.getFecha_creacion());
        emprendimientoActual.setEstado_emprendimiento(emprendimiento.getEstado_emprendimiento());
        emprendimientoActual.setIdregiones(emprendimiento.getIdregiones());
        emprendimientoActual.setIdusuarios(emprendimiento.getIdusuarios());

        if (imagenFile != null && !imagenFile.isEmpty()) {
            try {
                emprendimientoActual.setImagen_emprendimiento(imagenFile.getBytes());
            } catch (IOException ex) {
                ex.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

        return ResponseEntity.ok(emprendimientoService.guardar(emprendimientoActual));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (!emprendimientoService.obtenerPorId(id).isPresent()){
            System.out.println("No se encontró el emprendimiento con id " + id);
            return ResponseEntity.badRequest().build();
        }
        emprendimientoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
