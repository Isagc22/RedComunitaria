package com.example.proyectotalentotech.repository;

import com.example.proyectotalentotech.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la entidad Roles.
 * <p>
 * Esta interfaz proporciona métodos para acceder y manipular datos de roles
 * en la base de datos, incluyendo operaciones CRUD básicas heredadas de JpaRepository
 * sin implementar consultas personalizadas adicionales.
 * </p>
 * <p>
 * Los roles son asignaciones específicas que vinculan a un usuario con un tipo de usuario,
 * permitiendo definir los permisos y capacidades que cada usuario tiene en el sistema.
 * Cada rol incluye información sobre cuándo fue creado y modificado, proporcionando
 * una auditoría básica de los cambios en los permisos de usuarios.
 * </p>
 * 
 * @author Equipo RedComunitaria
 * @version 1.0
 * @since 2023-03-30
 */
@Repository
public interface RolesRepository extends JpaRepository<Roles, Integer> {
}

