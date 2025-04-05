package com.example.proyectotalentotech.repository;

import com.example.proyectotalentotech.model.DatosPersonales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para la entidad DatosPersonales.
 * <p>
 * Esta interfaz proporciona métodos para acceder y manipular los datos en la tabla
 * 'datospersonales' de la base de datos. Extiende JpaRepository para heredar
 * operaciones CRUD básicas y métodos de consulta de paginación.
 * </p>
 * 
 * @author Equipo RedComunitaria
 * @version 1.0
 * @since 2023-03-30
 */
@Repository
public interface DatosPersonalesRepository extends JpaRepository<DatosPersonales, Integer> {
    
    /**
     * Busca y devuelve los datos personales asociados a un usuario específico.
     * <p>
     * Este método permite recuperar información personal de un usuario basándose
     * en su identificador único, facilitando la visualización de perfiles y la 
     * gestión de información personal en el sistema.
     * </p>
     * 
     * @param idusuarios El ID del usuario cuyos datos personales se quieren obtener
     * @return Un Optional que puede contener los datos personales si existen, o estar vacío si no hay datos para ese usuario
     */
    Optional<DatosPersonales> findByIdusuarios(Integer idusuarios);
}


