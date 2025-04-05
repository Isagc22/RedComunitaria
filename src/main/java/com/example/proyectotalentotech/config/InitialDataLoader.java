package com.example.proyectotalentotech.config;

import com.example.proyectotalentotech.model.Roles;
import com.example.proyectotalentotech.model.Usuario;
import com.example.proyectotalentotech.repository.RolesRepository;
import com.example.proyectotalentotech.repository.UsuarioRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
@ConditionalOnProperty(name = "app.data.initialize", havingValue = "true", matchIfMissing = false)
public class InitialDataLoader {

    private final RolesRepository rolesRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    @Transactional
    public void loadInitialData() {
        log.info("Cargando datos iniciales...");
        
        // Crear roles si no existen
        createRoleIfNotExists(1, "ROLE_ADMIN");
        createRoleIfNotExists(2, "ROLE_USER");
        
        // Crear usuario admin si no existe
        createAdminUserIfNotExists();
        
        log.info("Datos iniciales cargados con éxito");
    }
    
    private void createRoleIfNotExists(int roleId, String roleName) {
        Optional<Roles> existingRole = rolesRepository.findById(roleId);
        
        if (existingRole.isEmpty()) {
            Roles role = new Roles();
            role.setIdroles(roleId);
            role.setNombreRol(roleName);
            role.setCreado(LocalDateTime.now());
            role.setModificado(LocalDateTime.now());
            role.setIdusuarios(1); // Usuario del sistema
            role.setIdtipousuario(1); // Tipo administrador
            
            rolesRepository.save(role);
            log.info("Rol creado: {}", roleName);
        } else {
            // Actualizar el nombre del rol si es necesario
            Roles role = existingRole.get();
            if (role.getNombreRol() == null || !role.getNombreRol().equals(roleName)) {
                role.setNombreRol(roleName);
                role.setModificado(LocalDateTime.now());
                rolesRepository.save(role);
                log.info("Rol actualizado: {}", roleName);
            }
        }
    }
    
    private void createAdminUserIfNotExists() {
        if (usuarioRepository.findByUsername("admin").isEmpty()) {
            Usuario admin = new Usuario();
            admin.setEmailUser("admin@example.com");
            admin.setUsername("admin");
            admin.setPassword_user(passwordEncoder.encode("admin123"));
            admin.setEstado_user(true);
            
            Roles adminRole = rolesRepository.findById(1)
                .orElseThrow(() -> new RuntimeException("Rol de administrador no encontrado"));
            admin.setRol(adminRole);
            
            usuarioRepository.save(admin);
            log.info("Usuario administrador creado");
        }
    }
} 