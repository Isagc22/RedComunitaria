package com.example.proyectotalentotech.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class DatabaseMigration {

    private final JdbcTemplate jdbcTemplate;

    @PostConstruct
    @Transactional
    public void migrate() {
        log.info("Iniciando migración de base de datos...");
        try {
            // 1. Añadir columna username a la tabla usuarios si no existe
            try {
                jdbcTemplate.execute("ALTER TABLE usuarios ADD COLUMN username VARCHAR(50)");
                log.info("Columna username añadida a la tabla usuarios");
            } catch (Exception e) {
                log.info("La columna username ya existe o no se pudo crear: {}", e.getMessage());
            }

            // 2. Actualizar los valores de username basados en email
            jdbcTemplate.execute("UPDATE usuarios SET username = email_user WHERE username IS NULL");
            log.info("Actualización de usernames completada");

            // 3. Hacer username único y no nulo
            try {
                jdbcTemplate.execute("ALTER TABLE usuarios MODIFY COLUMN username VARCHAR(50) NOT NULL UNIQUE");
                log.info("Columna username modificada para ser UNIQUE y NOT NULL");
            } catch (Exception e) {
                log.info("No se pudo modificar la columna username: {}", e.getMessage());
            }

            // 4. Añadir columna nombre_rol a la tabla roles si no existe
            try {
                jdbcTemplate.execute("ALTER TABLE roles ADD COLUMN nombre_rol VARCHAR(50)");
                log.info("Columna nombre_rol añadida a la tabla roles");
            } catch (Exception e) {
                log.info("La columna nombre_rol ya existe o no se pudo crear: {}", e.getMessage());
            }

            // 5. Poblar la tabla roles con valores por defecto
            jdbcTemplate.execute("UPDATE roles SET nombre_rol = 'ROLE_ADMIN' WHERE idroles = 1 AND (nombre_rol IS NULL OR nombre_rol = '')");
            jdbcTemplate.execute("UPDATE roles SET nombre_rol = 'ROLE_USER' WHERE idroles = 2 AND (nombre_rol IS NULL OR nombre_rol = '')");
            log.info("Valores por defecto establecidos para roles");

            // 6. Añadir columna idroles a usuarios si no existe
            try {
                jdbcTemplate.execute("ALTER TABLE usuarios ADD COLUMN idroles INT DEFAULT 2");
                log.info("Columna idroles añadida a la tabla usuarios");
            } catch (Exception e) {
                log.info("La columna idroles ya existe o no se pudo crear: {}", e.getMessage());
            }

            // 7. Añadir índice para idroles
            try {
                jdbcTemplate.execute("ALTER TABLE usuarios ADD INDEX idx_usuarios_roles (idroles)");
                log.info("Índice idx_usuarios_roles creado");
            } catch (Exception e) {
                log.info("El índice idx_usuarios_roles ya existe o no se pudo crear: {}", e.getMessage());
            }

            // 8. Añadir clave foránea
            try {
                jdbcTemplate.execute(
                    "ALTER TABLE usuarios ADD CONSTRAINT fk_usuarios_roles " +
                    "FOREIGN KEY (idroles) REFERENCES roles(idroles) " +
                    "ON DELETE SET NULL ON UPDATE CASCADE"
                );
                log.info("Constraint fk_usuarios_roles creado");
            } catch (Exception e) {
                log.info("El constraint fk_usuarios_roles ya existe o no se pudo crear: {}", e.getMessage());
            }

            log.info("Migración de base de datos completada con éxito");
        } catch (Exception e) {
            log.error("Error durante la migración de la base de datos", e);
            throw new RuntimeException("Error en la migración de la base de datos", e);
        }
    }
} 