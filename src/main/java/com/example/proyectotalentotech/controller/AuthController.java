package com.example.proyectotalentotech.controller;

import com.example.proyectotalentotech.model.Roles;
import com.example.proyectotalentotech.model.Usuario;
import com.example.proyectotalentotech.repository.RolesRepository;
import com.example.proyectotalentotech.repository.UsuarioRepository;
import com.example.proyectotalentotech.security.JwtService;
import com.example.proyectotalentotech.security.dto.AuthRequestDTO;
import com.example.proyectotalentotech.security.dto.AuthResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final RolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequestDTO request) {
        log.info("Intento de login con usuario: {}", request.getUsername());
        
        // Identificar si el usuario existe (por username o por email)
        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(request.getUsername());
        if (usuarioOpt.isEmpty()) {
            usuarioOpt = usuarioRepository.findByEmailUser(request.getUsername());
        }
        
        if (usuarioOpt.isEmpty()) {
            log.warn("Usuario no encontrado: {}", request.getUsername());
            return ResponseEntity.badRequest().body("Usuario no encontrado");
        }
        
        Usuario usuario = usuarioOpt.get();
        
        // Verificar la contraseña directamente (para compatibilidad con contraseñas antiguas)
        if (usuario.getPassword_user().equals(request.getPassword())) {
            log.info("Autenticación exitosa con contraseña en texto plano para: {}", request.getUsername());
            
            // Generar token JWT
            String jwtToken = jwtService.generateToken(usuario);
            
            // Si el usuario tiene un rol nulo, asignar rol de usuario por defecto
            if (usuario.getRol() == null) {
                Roles rolUsuario = rolesRepository.findById(2)
                        .orElseThrow(() -> new RuntimeException("Rol de usuario no encontrado"));
                usuario.setRol(rolUsuario);
                usuarioRepository.save(usuario);
            }
            
            return ResponseEntity.ok(
                AuthResponseDTO.builder()
                    .token(jwtToken)
                    .idUsuario(usuario.getIdusuarios())
                    .username(usuario.getUsername())
                    .rol(usuario.getRol() != null ? usuario.getRol().getNombreRol() : "ROLE_USER")
                    .build()
            );
        }
        
        // Intento de autenticación con Spring Security (para contraseñas encriptadas)
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            usuario.getUsername(),
                            request.getPassword()
                    )
            );
            
            if (authentication.isAuthenticated()) {
                log.info("Autenticación exitosa con Spring Security para: {}", request.getUsername());
                
                String jwtToken = jwtService.generateToken(usuario);
                
                return ResponseEntity.ok(
                    AuthResponseDTO.builder()
                        .token(jwtToken)
                        .idUsuario(usuario.getIdusuarios())
                        .username(usuario.getUsername())
                        .rol(usuario.getRol() != null ? usuario.getRol().getNombreRol() : "ROLE_USER")
                        .build()
                );
            }
        } catch (AuthenticationException e) {
            log.warn("Error de autenticación con Spring Security: {}", e.getMessage());
        }
        
        log.warn("Credenciales incorrectas para usuario: {}", request.getUsername());
        return ResponseEntity.badRequest().body("Credenciales incorrectas");
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequestDTO request) {
        log.info("Intento de registro de usuario: {}", request.getUsername());
        
        // Verificar si el usuario ya existe
        if(usuarioRepository.findByUsername(request.getUsername()).isPresent() ||
           usuarioRepository.findByEmailUser(request.getUsername()).isPresent()) {
            log.warn("El usuario ya existe: {}", request.getUsername());
            return ResponseEntity.badRequest().body("El usuario ya existe");
        }
        
        // Crear un nuevo usuario
        Usuario usuario = new Usuario();
        usuario.setUsername(request.getUsername());
        usuario.setEmailUser(request.getUsername()); // Usamos el username como email
        usuario.setPassword_user(request.getPassword()); // Guardamos la contraseña en texto plano para compatibilidad
        usuario.setEstado_user(true);
        
        // Asignar rol por defecto (usuario normal)
        Roles rolUsuario = rolesRepository.findById(2)
                .orElseThrow(() -> new RuntimeException("Rol de usuario no encontrado"));
        usuario.setRol(rolUsuario);
        
        // Guardar el usuario
        usuarioRepository.save(usuario);
        log.info("Usuario registrado exitosamente: {}", request.getUsername());
        
        // Generar token
        String jwtToken = jwtService.generateToken(usuario);
        
        return ResponseEntity.ok(
            AuthResponseDTO.builder()
                .token(jwtToken)
                .idUsuario(usuario.getIdusuarios())
                .username(usuario.getUsername())
                .rol(usuario.getRol().getNombreRol())
                .build()
        );
    }
} 