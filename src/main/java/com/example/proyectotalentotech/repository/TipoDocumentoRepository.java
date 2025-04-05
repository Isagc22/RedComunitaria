package com.example.proyectotalentotech.repository;

import com.example.proyectotalentotech.model.TipoDocumento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la entidad TipoDocumento.
 * <p>
 * Esta interfaz proporciona métodos para acceder y manipular datos de tipos de documento
 * en la base de datos, incluyendo operaciones CRUD básicas heredadas de JpaRepository
 * sin implementar consultas personalizadas adicionales.
 * </p>
 * <p>
 * Los tipos de documento representan las diferentes categorías de documentos de identificación
 * que pueden utilizar los usuarios, como cédula de ciudadanía, pasaporte, etc., y son
 * esenciales para la gestión de la identidad de los usuarios en el sistema.
 * </p>
 * 
 * @author Equipo RedComunitaria
 * @version 1.0
 * @since 2023-03-30
 */
@Repository
public interface TipoDocumentoRepository extends JpaRepository<TipoDocumento, Integer> {
}
