package com.example.proyectotalentotech.controller;

import com.example.proyectotalentotech.model.DatosPersonales;
import com.example.proyectotalentotech.services.DatosPersonalesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/datosPersonales")
public class DatosPersonalesController {

    private final DatosPersonalesService datosPersonalesService;

    public DatosPersonalesController(DatosPersonalesService datosPersonalesService) {
        this.datosPersonalesService = datosPersonalesService;

    }

    @PostMapping("/{idUsuario}/{idTipoDocumento}")
    public ResponseEntity<DatosPersonales> crearDatosPersonales(@RequestBody DatosPersonales datosPersonales) {
        return ResponseEntity.ok(datosPersonalesService.guardar(datosPersonales));
    }


    @GetMapping
    public ResponseEntity<List<DatosPersonales>> obtenerDatosPersonales() {
        return ResponseEntity.ok(datosPersonalesService.listarTodos());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        datosPersonalesService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
