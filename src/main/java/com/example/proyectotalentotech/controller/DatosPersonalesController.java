package com.example.proyectotalentotech.controller;

import com.example.proyectotalentotech.model.DatosPersonales;
import com.example.proyectotalentotech.services.DatosPersonalesService;
import com.example.proyectotalentotech.services.TipoDocumentoService;
import com.example.proyectotalentotech.services.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/datosPersonales")
public class DatosPersonalesController {

    private final DatosPersonalesService datosPersonalesService;
    private final UsuarioService usuarioService;
    private final TipoDocumentoService tipoDocumentoService;

    public DatosPersonalesController(DatosPersonalesService datosPersonalesService,
                                     UsuarioService usuarioService,
                                     TipoDocumentoService tipoDocumentoService) {
        this.datosPersonalesService = datosPersonalesService;
        this.usuarioService = usuarioService;
        this.tipoDocumentoService = tipoDocumentoService;

    }

    @PostMapping
    public ResponseEntity<DatosPersonales> crear(@RequestBody DatosPersonales datosPersonales) {
        if (!usuarioService.obtenerPorId(datosPersonales.getIdusuarios()).isPresent()) {
            System.out.println("Usuario con id " + datosPersonales.getIdusuarios() + " no existe");
            return ResponseEntity.badRequest().body(null);
        }
        if (!tipoDocumentoService.obtenerPorId(datosPersonales.getIdtipodocumento()).isPresent()) {
            System.out.println("Tipo de documento con id " + datosPersonales.getIdtipodocumento() + " no existe");
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(datosPersonalesService.guardar(datosPersonales));
    }

    @GetMapping
    public ResponseEntity<List<DatosPersonales>> listarTodos() {
        List<DatosPersonales> lista = datosPersonalesService.listarTodos();

        if (lista.isEmpty()){
            System.out.println("⚠️ La tabla de datos personales está vacía.");
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(lista);
    }


    @GetMapping("/{id}")
    public ResponseEntity<DatosPersonales> obtenerPorId(@PathVariable Integer id) {
        Optional<DatosPersonales> entity = datosPersonalesService.obtenerPorId(id);

        if (entity.isEmpty()) {
            System.out.println("No se encontró el dato personal con id " + id);
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(entity.get());
    }


    @PutMapping("/{id}")
    public ResponseEntity<DatosPersonales> editarPorId(@PathVariable Integer id, @RequestBody DatosPersonales datosPersonales) {
        Optional<DatosPersonales> entity = datosPersonalesService.editarPorId(id);

        if (entity.isPresent()) {
            DatosPersonales datos = entity.get();

            datos.setNombre_completo(datosPersonales.getNombre_completo());
            datos.setCedula(datosPersonales.getCedula());
            datos.setDireccion(datosPersonales.getDireccion());
            datos.setTelefono(datosPersonales.getTelefono());
            datos.setImagen(datosPersonales.getImagen());
            datos.setIdusuarios(datosPersonales.getIdusuarios());
            datos.setIdtipodocumento(datosPersonales.getIdtipodocumento());
            if (!usuarioService.obtenerPorId(datos.getIdusuarios()).isPresent()) {
                System.out.println("Usuario con id " + datos.getIdusuarios() + " no existe");
                return ResponseEntity.badRequest().body(null);
            }

            if (!tipoDocumentoService.obtenerPorId(datos.getIdtipodocumento()).isPresent()) {
                System.out.println("Tipo de documento con id " + datos.getIdtipodocumento() + " no existe");
                return ResponseEntity.badRequest().body(null);
            }
            return ResponseEntity.ok(datosPersonalesService.guardar(datos));
        } else {
            System.out.println("No se encontró el dato personal con id " + id);
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {

        if (!datosPersonalesService.obtenerPorId(id).isPresent()) {
            System.out.println("Usuario con id " + id + " no existe");
            return ResponseEntity.badRequest().build();
        }

        datosPersonalesService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
