package com.example.proyectotalentotech.controller;

import com.example.proyectotalentotech.model.DatosPersonales;
import com.example.proyectotalentotech.model.TipoDocumento;
import com.example.proyectotalentotech.model.Usuario;
import com.example.proyectotalentotech.services.DatosPersonalesService;
import com.example.proyectotalentotech.services.TipoDocumentoService;
import com.example.proyectotalentotech.services.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/{idUsuario}/{idTipoDocumento}")
    public ResponseEntity<DatosPersonales> crearDatosPersonales(
            @PathVariable Integer idUsuario,
            @PathVariable Integer idTipoDocumento,
            @RequestBody DatosPersonales datosPersonales) {

        // Verifica que el usuario existe
        Optional<Usuario> usuarioOpt = usuarioService.obtenerPorId(idUsuario);
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        // Verifica que el tipo de documento existe
        Optional<TipoDocumento> tipoDocumentoOpt = tipoDocumentoService.obtenerPorId(idTipoDocumento);
        if (tipoDocumentoOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        // Asigna los valores correctos antes de guardar
        datosPersonales.setUsuario(usuarioOpt.get());
        datosPersonales.setTipoDocumento(tipoDocumentoOpt.get());

        // Guarda los datos personales
        return ResponseEntity.ok(datosPersonalesService.guardar(datosPersonales));
    }
}
