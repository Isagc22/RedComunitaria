package com.example.proyectotalentotech.controller;

import com.example.proyectotalentotech.model.TipoDocumento;
import com.example.proyectotalentotech.services.TipoDocumentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tipodocumento")
public class TipoDocumentoController {

    private final TipoDocumentoService tipoDocumentoService;

    public TipoDocumentoController(TipoDocumentoService tipoDocumentoService) {
        this.tipoDocumentoService = tipoDocumentoService;
    }

    @PostMapping
    public ResponseEntity<TipoDocumento> crear(@RequestBody TipoDocumento tipoDocumento) {
        return ResponseEntity.ok(tipoDocumentoService.guardar(tipoDocumento));
    }

    @GetMapping
    public ResponseEntity<List<TipoDocumento>> listarTodos() {
        List<TipoDocumento> lista = tipoDocumentoService.listarTodos();
        if (lista.isEmpty()) {
            System.out.println("⚠️ La tabla de tipo de documento está vacía.");
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoDocumento> obtenerPorId(@PathVariable Integer id) {
        Optional<TipoDocumento> entity = tipoDocumentoService.obtenerPorId(id);
        if (entity.isEmpty()) {
            System.out.println("No se encontró el tipo de documento con id " + id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(entity.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoDocumento> actualizar(@PathVariable Integer id, @RequestBody TipoDocumento tipoDocumento) {
        Optional<TipoDocumento> entity = tipoDocumentoService.editarPorId(id);

        if (entity.isPresent()) {
            TipoDocumento tipoDocumentoActual = entity.get();
            tipoDocumentoActual.setNombre_tipo_documento(tipoDocumento.getNombre_tipo_documento());
            return ResponseEntity.ok(tipoDocumentoService.guardar(tipoDocumentoActual));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (!tipoDocumentoService.obtenerPorId(id).isPresent()) {
            System.out.println("No se encontró el tipo de documento con id " + id);
            return ResponseEntity.badRequest().build();
        }
        tipoDocumentoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}