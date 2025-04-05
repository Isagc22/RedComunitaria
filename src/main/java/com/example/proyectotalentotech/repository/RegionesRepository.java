package com.example.proyectotalentotech.repository;

import com.example.proyectotalentotech.model.Regiones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la entidad Regiones.
 * <p>
 * Esta interfaz proporciona métodos para acceder y manipular datos de regiones
 * geográficas en la base de datos, incluyendo operaciones CRUD básicas heredadas
 * de JpaRepository sin implementar consultas personalizadas adicionales.
 * </p>
 * <p>
 * Las regiones representan divisiones territoriales que son utilizadas para
 * clasificar y ubicar emprendimientos dentro del sistema.
 * </p>
 * 
 * @author Equipo RedComunitaria
 * @version 1.0
 * @since 2023-03-30
 */
@Repository
public interface RegionesRepository extends JpaRepository<Regiones, Integer> {
}
