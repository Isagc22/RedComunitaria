package com.example.proyectotalentotech.services;

import com.example.proyectotalentotech.model.DatosPersonales;
import com.example.proyectotalentotech.repository.DatosPersonalesRepository;
import com.example.proyectotalentotech.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

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
    private final UsuarioRepository usuarioRepository;
    
    /**
     * Constructor del servicio que recibe el repositorio de datos personales y el repositorio de usuarios mediante inyección de dependencias.
     * 
     * @param datosPersonalesRepository Repositorio para acceder a los datos personales
     * @param usuarioRepository Repositorio para acceder a los usuarios
     */
    public DatosPersonalesService(DatosPersonalesRepository datosPersonalesRepository, 
                                 UsuarioRepository usuarioRepository) {
        this.datosPersonalesRepository = datosPersonalesRepository;
        this.usuarioRepository = usuarioRepository;
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
     * @param idUsuario el ID del usuario
     * @return Optional con los datos personales encontrados o creados
     */
    public Optional<DatosPersonales> obtenerPorIdUsuario(Integer idUsuario) {
        // Primero intentamos buscar por el ID de usuario
        Optional<DatosPersonales> datos = datosPersonalesRepository.findByIdusuarios(idUsuario);
        
        // Si existen datos, los devolvemos
        if (datos.isPresent()) {
            System.out.println("Datos personales encontrados para el usuario ID: " + idUsuario);
            return datos;
        }
        
        // Si no existen datos pero el usuario existe, creamos un registro básico
        if (usuarioRepository.findById(idUsuario).isPresent()) {
            System.out.println("Creando registro de datos personales básico para el usuario ID: " + idUsuario);
            
            // Creamos un objeto con datos básicos
            DatosPersonales nuevosDatos = new DatosPersonales();
            nuevosDatos.setIdusuarios(idUsuario);
            nuevosDatos.setNombre_completo("");
            nuevosDatos.setCedula("");
            nuevosDatos.setDireccion("");
            nuevosDatos.setTelefono("");
            // Usando un ID de tipo de documento por defecto (ajustar según la base de datos)
            nuevosDatos.setIdtipodocumento(1);
            
            // Guardamos el nuevo registro
            try {
                DatosPersonales guardado = datosPersonalesRepository.save(nuevosDatos);
                return Optional.of(guardado);
            } catch (Exception e) {
                System.err.println("Error al crear datos personales básicos: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("No se encontró el usuario con ID: " + idUsuario);
        }
        
        return Optional.empty();
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
