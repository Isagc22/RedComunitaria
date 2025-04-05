package com.example.proyectotalentotech.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Controlador para endpoints generales de la API.
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ApiController {

    /**
     * Endpoint de verificación de salud de la API.
     * 
     * @return ResponseEntity con información sobre el estado del sistema
     */
    @GetMapping("/health-check")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("message", "API funcionando correctamente");
        response.put("timestamp", new Date());
        
        return ResponseEntity.ok(response);
    }
} 