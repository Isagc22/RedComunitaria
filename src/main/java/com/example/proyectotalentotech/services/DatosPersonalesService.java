package com.example.proyectotalentotech.services;

import com.example.proyectotalentotech.model.DatosPersonales;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.proyectotalentotech.repository.DatosPersonalesRepository;

import java.util.List;
import java.util.Optional;

/**
 * Servicio que gestiona las operaciones de negocio relacionadas con los datos personales.
 * <p>
 * Esta clase proporciona métodos para crear, leer, actualizar y eliminar datos personales,
 * así como funcionalidades adicionales para buscar datos por usuario.
 * </p>
 * 
 * @author Equipo RedComunitaria
 * @version 1.0
 * @since 2023-03-30
 */
@Service
public class DatosPersonalesService {

    private final DatosPersonalesRepository datosPersonalesRepository;
    
    /**
     * Constructor del servicio que recibe el repositorio de datos personales mediante inyección de dependencias.
     * 
     * @param datosPersonalesRepository Repositorio para acceder a los datos personales
     */
    @Autowired
    public DatosPersonalesService(DatosPersonalesRepository datosPersonalesRepository) {
        this.datosPersonalesRepository = datosPersonalesRepository;
    }
    
    /**
     * Obtiene una lista de todos los datos personales almacenados en el sistema.
     * 
     * @return Lista con todos los registros de datos personales
     */
    public List<DatosPersonales> listarTodos() {
        return datosPersonalesRepository.findAll();
    }
    
    /**
     * Busca y devuelve los datos personales correspondientes al ID proporcionado.
     * 
     * @param id Identificador único de los datos personales
     * @return Optional que puede contener los datos personales si existen
     */
    public Optional<DatosPersonales> obtenerPorId(Integer id) {
        return datosPersonalesRepository.findById(id);
    }
    
    /**
     * Busca los datos personales para edición según el ID proporcionado.
     * Este método es similar a obtenerPorId pero se utiliza específicamente
     * en contextos de edición.
     * 
     * @param id Identificador único de los datos personales
     * @return Optional que puede contener los datos personales si existen
     */
    public Optional<DatosPersonales> editarPorId(Integer id) {
        return datosPersonalesRepository.findById(id);
    }
    
    /**
     * Método para obtener datos personales por ID de usuario.
     * <p>
     * Busca los datos personales asociados a un usuario específico e incluye
     * registro de logs para facilitar la depuración.
     * </p>
     * 
     * @param idusuarios ID del usuario del que se quieren obtener los datos personales
     * @return Optional con los datos personales si se encontraron
     */
    public Optional<DatosPersonales> obtenerPorIdUsuario(Integer idusuarios) {
        try {
            System.out.println("Servicio - Buscando datos personales para usuario ID: " + idusuarios);
            Optional<DatosPersonales> datos = datosPersonalesRepository.findByIdusuarios(idusuarios);
            if (datos.isPresent()) {
                System.out.println("Servicio - Datos personales encontrados para usuario ID: " + idusuarios);
            } else {
                System.out.println("Servicio - No se encontraron datos personales para usuario ID: " + idusuarios);
            }
            return datos;
        } catch (Exception e) {
            System.err.println("Servicio - Error al buscar datos personales para usuario ID " + idusuarios + ": " + e.getMessage());
            e.printStackTrace();
            return Optional.empty();
        }
    }
    
    /**
     * Guarda o actualiza los datos personales en la base de datos.
     * <p>
     * Si el objeto tiene un ID existente, actualizará ese registro.
     * Si no tiene ID o el ID no existe, creará un nuevo registro.
     * </p>
     * 
     * @param datosPersonales Objeto con los datos personales a guardar o actualizar
     * @return El objeto DatosPersonales guardado, incluyendo su ID generado si es una creación
     */
    public DatosPersonales guardar(DatosPersonales datosPersonales) {
        return datosPersonalesRepository.save(datosPersonales);
    }
    
    /**
     * Elimina los datos personales correspondientes al ID proporcionado.
     * 
     * @param id Identificador único de los datos personales a eliminar
     */
    public void eliminar(Integer id) {
        datosPersonalesRepository.deleteById(id);
    }
}
