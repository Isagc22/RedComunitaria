package com.example.proyectotalentotech.controller;

import com.example.proyectotalentotech.model.TipoDocumento;
import com.example.proyectotalentotech.model.TipoUsuario;
import com.example.proyectotalentotech.services.TipoDocumentoService;
import com.example.proyectotalentotech.services.TipoUsuarioServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tipoDocumento")
public class TipoDocumentoController {
    private final TipoDocumentoService tipoDocumentoService;

    public TipoDocumentoController(TipoDocumentoService tipodocumentoservice) {
        this.tipoDocumentoService = tipodocumentoservice;
    }

    @PostMapping
    public ResponseEntity<TipoDocumento> crearTipoDocumento(@RequestBody TipoDocumento tipoDocumento) {
        return ResponseEntity.ok(tipoDocumentoService.guardar(tipoDocumento));

    }

    @GetMapping
    public ResponseEntity<List<TipoDocumento>> obtenerTipoDocumentos() {
        return ResponseEntity.ok(tipoDocumentoService.listarTipoDocumento());
    }
}