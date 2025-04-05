package com.example.proyectotalentotech.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseDTO {
    private String token;
    private String tipo = "Bearer";
    private Integer idUsuario;
    private String username;
    private String rol;
} 