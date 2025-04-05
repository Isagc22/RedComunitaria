package com.example.proyectotalentotech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * Clase principal de la aplicación Spring Boot RedComunitaria.
 * <p>
 * Esta clase contiene el método main que inicia la aplicación Spring Boot y configura
 * el contenedor de aplicación. Utiliza las anotaciones estándar de Spring Boot para
 * habilitar la configuración automática y el escaneo de componentes.
 * </p>
 * 
 * @author Equipo RedComunitaria
 * @version 1.0
 * @since 2023-03-30
 */
@SpringBootApplication
public class ProyectoTalentoTechApplication {

    /**
     * Método principal que inicia la aplicación Spring Boot.
     * 
     * @param args Argumentos de línea de comandos pasados a la aplicación
     */
    public static void main(String[] args) {
        SpringApplication.run(ProyectoTalentoTechApplication.class, args);
    }
}
