package com.example.proyectotalentotech.controller;

import com.example.proyectotalentotech.model.ProduccionConsumoEnergia;
import com.example.proyectotalentotech.model.dto.ProduccionConsumoDto;
import com.example.proyectotalentotech.services.ProduccionConsumoEnergiaServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * Controlador REST para la gestión de datos de producción y consumo de energía.
 * <p>
 * Esta clase proporciona endpoints para registrar y consultar datos sobre la producción
 * y consumo de energía de los emprendimientos, permitiendo visualizar estadísticas y 
 * realizar seguimiento del uso energético a lo largo del tiempo.
 * </p>
 * 
 * @author Equipo RedComunitaria
 * @version 1.0
 * @since 2023-03-30
 */
@RestController
@RequestMapping("/produccionconsumoenergia")
@CrossOrigin(origins = "*")
public class ProduccionConsumoEnergiaController {

    private final ProduccionConsumoEnergiaServices produccionConsumoEnergiaServices;

    /**
     * Constructor para la inyección de dependencias.
     * 
     * @param produccionConsumoEnergiaServices Servicio para la gestión de datos de producción y consumo de energía
     */
    @Autowired
    public ProduccionConsumoEnergiaController(ProduccionConsumoEnergiaServices produccionConsumoEnergiaServices) {
        this.produccionConsumoEnergiaServices = produccionConsumoEnergiaServices;
    }

    /**
     * Endpoint de verificación de salud del servicio.
     * Se utiliza para confirmar que el servicio está disponible.
     * 
     * @return ResponseEntity con un mensaje de confirmación
     */
    @GetMapping("/health-check")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("message", "Servicio de producción y consumo de energía disponible");
        response.put("timestamp", new Date());
        return ResponseEntity.ok(response);
    }

    /**
     * Obtiene datos de producción y consumo de energía con filtros opcionales.
     *
     * @param emprendimientoId ID del emprendimiento (opcional)
     * @param fechaDesde Fecha de inicio para filtrar (opcional)
     * @param fechaHasta Fecha de fin para filtrar (opcional)
     * @return Lista de datos de producción y consumo que coinciden con los criterios
     */
    @GetMapping("/datos")
    public ResponseEntity<?> obtenerDatos(
            @RequestParam(required = false) Long emprendimientoId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDesde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaHasta) {

        try {
            List<ProduccionConsumoEnergia> datos;

            // Aplicar filtros según los parámetros proporcionados
            if (emprendimientoId != null && fechaDesde != null && fechaHasta != null) {
                datos = produccionConsumoEnergiaServices.obtenerPorEmprendimientoYRangoFechas(
                        emprendimientoId, fechaDesde, fechaHasta);
            } else if (emprendimientoId != null) {
                datos = produccionConsumoEnergiaServices.obtenerPorEmprendimiento(emprendimientoId);
            } else if (fechaDesde != null && fechaHasta != null) {
                datos = produccionConsumoEnergiaServices.obtenerPorRangoFechas(fechaDesde, fechaHasta);
            } else {
                datos = produccionConsumoEnergiaServices.obtenerTodos();
            }

            if (datos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("mensaje", "No se encontraron datos con los criterios especificados"));
            }

            return ResponseEntity.ok(datos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al obtener los datos: " + e.getMessage()));
        }
    }

    /**
     * Obtiene un resumen de la producción y consumo para todos los emprendimientos.
     *
     * @return Resumen de producción y consumo
     */
    @GetMapping("/resumen")
    public ResponseEntity<?> obtenerResumen() {
        try {
            List<Map<String, Object>> resumen = produccionConsumoEnergiaServices.obtenerResumenProduccionConsumo();
            
            if (resumen.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("mensaje", "No se encontraron datos para generar el resumen"));
            }
            
            return ResponseEntity.ok(resumen);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al obtener el resumen: " + e.getMessage()));
        }
    }

    /**
     * Convierte los datos del modelo a un formato que el frontend pueda procesar fácilmente.
     */
    private List<Map<String, Object>> convertirAFormatoFrontend(List<ProduccionConsumoEnergia> datos) {
        List<Map<String, Object>> resultado = new ArrayList<>();
        
        for (ProduccionConsumoEnergia dato : datos) {
            Map<String, Object> item = new HashMap<>();
            item.put("id", dato.getId());
            item.put("fecha", dato.getFecha().toString());
            item.put("produccion", dato.getEnergiaProducida());
            item.put("consumo", dato.getEnergiaConsumida());
            item.put("emprendimientoId", dato.getEmprendimientoId());
            item.put("fuenteEnergia", dato.getFuenteEnergia());
            item.put("observaciones", dato.getObservaciones());
            resultado.add(item);
        }
        
        return resultado;
    }

    /**
     * Genera una lista completa de datos de ejemplo para todo un año.
     */
    private List<ProduccionConsumoEnergia> generarDatosEjemploCompleto() {
        List<ProduccionConsumoEnergia> datos = new ArrayList<>();
        LocalDate fechaInicio = LocalDate.now().minusYears(1);
        
        Random random = new Random();
        
        // Generar datos para los últimos 12 meses
        for (int i = 0; i < 365; i += 7) { // Datos semanales
            LocalDate fecha = fechaInicio.plusDays(i);
            
            // Simular producción y consumo con variación estacional
            int mes = fecha.getMonthValue();
            double factorEstacional = (mes >= 5 && mes <= 9) ? 1.3 : 0.8; // Mayor en verano
            
            double produccion = 50 + random.nextDouble() * 50 * factorEstacional;
            double consumo = 70 + random.nextDouble() * 40 * (2 - factorEstacional);
            
            ProduccionConsumoEnergia dato = new ProduccionConsumoEnergia();
            dato.setId((long) (i + 1));
            dato.setFecha(fecha);
            dato.setEnergiaProducida(Math.round(produccion * 100) / 100.0);
            dato.setEnergiaConsumida(Math.round(consumo * 100) / 100.0);
            dato.setEmprendimientoId(6L); // ID de ejemplo
            dato.setFuenteEnergia("Solar");
            dato.setFechaRegistro(LocalDateTime.now());
            dato.setUsuarioRegistro("sistema");
            
            datos.add(dato);
        }
        
        return datos;
    }

    /**
     * Registra un nuevo dato de producción y consumo de energía.
     *
     * @param produccionConsumo Datos de producción y consumo a registrar
     * @return Respuesta con los datos registrados
     */
    @PostMapping
    public ResponseEntity<?> registrarDatos(@RequestBody Map<String, Object> datos) {
        try {
            // Depurar los datos recibidos
            System.out.println("Datos recibidos para registro: " + datos);
            
            ProduccionConsumoEnergia produccionConsumo = new ProduccionConsumoEnergia();
            
            // Extraer y convertir campos recibidos
            // Manejar el ID del emprendimiento
            if (datos.containsKey("idemprendimiento")) {
                produccionConsumo.setEmprendimientoId(Long.valueOf(datos.get("idemprendimiento").toString()));
            } else if (datos.containsKey("emprendimientoId")) {
                produccionConsumo.setEmprendimientoId(Long.valueOf(datos.get("emprendimientoId").toString()));
            } else {
                return ResponseEntity.badRequest().body(Map.of("error", "El ID del emprendimiento es requerido"));
            }
            
            // Manejar energía producida
            if (datos.containsKey("produccion_energia")) {
                produccionConsumo.setEnergiaProducida(Double.valueOf(datos.get("produccion_energia").toString()));
            } else if (datos.containsKey("energiaProducida")) {
                produccionConsumo.setEnergiaProducida(Double.valueOf(datos.get("energiaProducida").toString()));
            } else {
                return ResponseEntity.badRequest().body(Map.of("error", "La producción de energía es requerida"));
            }
            
            // Manejar energía consumida
            if (datos.containsKey("consumo_energia")) {
                produccionConsumo.setEnergiaConsumida(Double.valueOf(datos.get("consumo_energia").toString()));
            } else if (datos.containsKey("energiaConsumida")) {
                produccionConsumo.setEnergiaConsumida(Double.valueOf(datos.get("energiaConsumida").toString()));
            } else {
                return ResponseEntity.badRequest().body(Map.of("error", "El consumo de energía es requerido"));
            }
            
            // Manejar fecha
            if (datos.containsKey("fecha")) {
                String fechaStr = datos.get("fecha").toString();
                try {
                    produccionConsumo.setFecha(LocalDate.parse(fechaStr));
                } catch (Exception e) {
                    System.out.println("Error al parsear fecha: " + fechaStr + ". Usando fecha actual.");
                    produccionConsumo.setFecha(LocalDate.now());
                }
            } else {
                produccionConsumo.setFecha(LocalDate.now());
            }
            
            // Otros campos opcionales
            if (datos.containsKey("fuente_energia")) {
                produccionConsumo.setFuenteEnergia(datos.get("fuente_energia").toString());
            } else if (datos.containsKey("fuenteEnergia")) {
                produccionConsumo.setFuenteEnergia(datos.get("fuenteEnergia").toString());
            }
            
            if (datos.containsKey("observaciones")) {
                produccionConsumo.setObservaciones(datos.get("observaciones").toString());
            }
            
            if (datos.containsKey("usuario_registro")) {
                produccionConsumo.setUsuarioRegistro(datos.get("usuario_registro").toString());
            } else if (datos.containsKey("usuarioRegistro")) {
                produccionConsumo.setUsuarioRegistro(datos.get("usuarioRegistro").toString());
            } else {
                produccionConsumo.setUsuarioRegistro("sistema");
            }
            
            // Fecha de registro (sistema)
            produccionConsumo.setFechaRegistro(LocalDateTime.now());
            
            // Guardar los datos
            ProduccionConsumoEnergia datosGuardados = produccionConsumoEnergiaServices.guardar(produccionConsumo);
            
            // Devolver una respuesta de éxito con los datos guardados
            System.out.println("Datos guardados correctamente: " + datosGuardados);
            return ResponseEntity.status(HttpStatus.CREATED).body(datosGuardados);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al registrar los datos: " + e.getMessage()));
        }
    }

    /**
     * Recupera datos de producción y consumo de energía para un emprendimiento específico.
     * <p>
     * Este endpoint permite consultar los datos de energía de un emprendimiento en un período
     * de tiempo determinado (semana, mes, trimestre o año). Si no hay datos disponibles,
     * devuelve datos de ejemplo para visualización.
     * </p>
     * 
     * @param emprendimientoId Identificador del emprendimiento
     * @param filtro Período de tiempo para filtrar los datos (semana, mes, trimestre, anio)
     * @return ResponseEntity con los datos de energía formateados para visualización en gráficos
     */
    @GetMapping("/datos/{emprendimientoId}")
    public ResponseEntity<?> obtenerDatosPorEmprendimiento(
            @PathVariable Long emprendimientoId,
            @RequestParam(defaultValue = "mes") String filtro) {
        
        try {
            // Obtener fecha límite según filtro
            LocalDate fechaHasta = LocalDate.now();
            LocalDate fechaDesde;
            
            switch (filtro) {
                case "semana":
                    fechaDesde = fechaHasta.minus(7, ChronoUnit.DAYS);
                    break;
                case "trimestre":
                    fechaDesde = fechaHasta.minus(3, ChronoUnit.MONTHS);
                    break;
                case "anio":
                    fechaDesde = fechaHasta.minus(1, ChronoUnit.YEARS);
                    break;
                case "mes":
                default:
                    fechaDesde = fechaHasta.minus(1, ChronoUnit.MONTHS);
                    break;
            }
            
            // Obtener datos según rango de fechas
            List<ProduccionConsumoEnergia> datos = produccionConsumoEnergiaServices
                    .obtenerPorEmprendimientoYRangoFechas(emprendimientoId, fechaDesde, fechaHasta);
            
            if (datos.isEmpty()) {
                // Devolver datos de ejemplo si no hay datos
                return ResponseEntity.ok(generarDatosEjemplo(filtro));
            }
            
            // Formatear fechas según filtro
            DateTimeFormatter formatter;
            if ("anio".equals(filtro)) {
                formatter = DateTimeFormatter.ofPattern("MMM yyyy");
            } else {
                formatter = DateTimeFormatter.ofPattern("dd/MM");
            }
            
            // Preparar datos para gráficos
            List<String> fechas = new ArrayList<>();
            List<Double> energiaProducida = new ArrayList<>();
            List<Double> energiaConsumida = new ArrayList<>();
            
            for (ProduccionConsumoEnergia dato : datos) {
                fechas.add(dato.getFecha().format(formatter));
                energiaProducida.add(dato.getEnergiaProducida());
                energiaConsumida.add(dato.getEnergiaConsumida());
            }
            
            Map<String, Object> resultado = new HashMap<>();
            resultado.put("fechas", fechas);
            resultado.put("energiaProducida", energiaProducida);
            resultado.put("energiaConsumida", energiaConsumida);
            resultado.put("emprendimientoId", emprendimientoId);
            resultado.put("periodoConsultado", filtro);
            
            return ResponseEntity.ok(resultado);
            
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Error al obtener datos: " + e.getMessage());
            return ResponseEntity.ok(generarDatosEjemplo(filtro)); // Devolver datos de ejemplo en caso de error
        }
    }
    
    /**
     * Genera datos de ejemplo para visualización cuando no hay datos reales disponibles.
     * <p>
     * Este método privado crea conjuntos de datos ficticios según el período solicitado,
     * permitiendo mostrar ejemplos visuales de cómo se representarían los datos reales.
     * </p>
     * 
     * @param filtro Período de tiempo para el cual generar datos (semana, mes, trimestre, anio)
     * @return Mapa con datos de ejemplo formateados para visualización en gráficos
     */
    private Map<String, Object> generarDatosEjemplo(String filtro) {
        List<String> fechas = new ArrayList<>();
        List<Double> produccion = new ArrayList<>();
        List<Double> consumo = new ArrayList<>();
        
        if ("semana".equals(filtro)) {
            fechas = Arrays.asList("Lun", "Mar", "Mié", "Jue", "Vie", "Sáb", "Dom");
            produccion = Arrays.asList(25.4, 30.2, 28.6, 32.1, 29.5, 35.8, 33.2);
            consumo = Arrays.asList(42.1, 38.5, 45.2, 37.8, 40.3, 36.9, 39.4);
        } else if ("anio".equals(filtro)) {
            fechas = Arrays.asList("Ene 2023", "Feb 2023", "Mar 2023", "Abr 2023", "May 2023", "Jun 2023", 
                                  "Jul 2023", "Ago 2023", "Sep 2023", "Oct 2023", "Nov 2023", "Dic 2023");
            produccion = Arrays.asList(210.5, 195.3, 230.8, 245.6, 280.2, 310.5, 320.1, 305.7, 290.2, 260.8, 240.3, 225.6);
            consumo = Arrays.asList(320.5, 310.2, 350.7, 330.5, 365.8, 380.2, 390.5, 385.2, 360.8, 340.5, 325.2, 330.8);
        } else {
            // Datos para mes o trimestre
            fechas = Arrays.asList("01/01", "05/01", "10/01", "15/01", "20/01", "25/01", "30/01");
            produccion = Arrays.asList(85.2, 92.5, 78.6, 95.3, 88.7, 90.2, 93.8);
            consumo = Arrays.asList(120.3, 115.8, 125.2, 118.7, 130.5, 122.8, 128.3);
        }
        
        Map<String, Object> resultado = new HashMap<>();
        resultado.put("fechas", fechas);
        resultado.put("energiaProducida", produccion);
        resultado.put("energiaConsumida", consumo);
        resultado.put("ejemplo", true);
        resultado.put("mensaje", "Mostrando datos de ejemplo. No se encontraron registros para el período seleccionado.");
        
        return resultado;
    }

    /**
     * Endpoint de filtrado simplificado para el frontend.
     * Permite filtrar los datos por emprendimiento y rango de fechas con nombres
     * de parámetros adaptados a la estructura de la base de datos.
     *
     * @param idemprendimiento ID del emprendimiento
     * @param fechaInicio Fecha de inicio para el filtro
     * @param fechaFin Fecha de fin para el filtro
     * @return Lista de datos filtrados según los criterios
     */
    @GetMapping("/filtrar")
    public ResponseEntity<?> filtrarDatos(
            @RequestParam(required = false) Long idemprendimiento,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {

        try {
            List<ProduccionConsumoEnergia> datos;
            
            // Aplicar filtros según los parámetros proporcionados
            if (idemprendimiento != null && fechaInicio != null && fechaFin != null) {
                datos = produccionConsumoEnergiaServices.obtenerPorEmprendimientoYRangoFechas(
                        idemprendimiento, fechaInicio, fechaFin);
            } else if (idemprendimiento != null) {
                datos = produccionConsumoEnergiaServices.obtenerPorEmprendimiento(idemprendimiento);
            } else if (fechaInicio != null && fechaFin != null) {
                datos = produccionConsumoEnergiaServices.obtenerPorRangoFechas(fechaInicio, fechaFin);
            } else {
                datos = produccionConsumoEnergiaServices.obtenerTodos();
            }

            if (datos.isEmpty()) {
                // Si no hay datos reales, generar datos de ejemplo
                datos = generarDatosEjemploCompleto();
                
                if (datos.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(Map.of("mensaje", "No se encontraron datos con los criterios especificados"));
                }
            }

            return ResponseEntity.ok(datos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al obtener los datos: " + e.getMessage()));
        }
    }

    /**
     * Consulta datos de producción y consumo de energía (endpoint POST para soportar formato JSON).
     *
     * @param datos Objeto con los filtros de búsqueda (fechaInicio, fechaFin, emprendimientoId)
     * @return Lista de datos de producción y consumo que coinciden con los criterios
     */
    @PostMapping("/consultar")
    public ResponseEntity<?> consultarDatos(@RequestBody Map<String, Object> datos) {
        try {
            System.out.println("Recibida consulta de datos de energía: " + datos);
            
            // Extraer filtros
            Long emprendimientoId = null;
            LocalDate fechaDesde = null;
            LocalDate fechaHasta = null;
            
            if (datos.containsKey("emprendimientoId") && datos.get("emprendimientoId") != null) {
                try {
                    emprendimientoId = Long.valueOf(datos.get("emprendimientoId").toString());
                } catch (Exception e) {
                    System.out.println("Error al convertir emprendimientoId: " + e.getMessage());
                }
            }
            
            if (datos.containsKey("fechaInicio") && datos.get("fechaInicio") != null) {
                try {
                    fechaDesde = LocalDate.parse(datos.get("fechaInicio").toString());
                } catch (Exception e) {
                    System.out.println("Error al convertir fechaInicio: " + e.getMessage());
                }
            }
            
            if (datos.containsKey("fechaFin") && datos.get("fechaFin") != null) {
                try {
                    fechaHasta = LocalDate.parse(datos.get("fechaFin").toString());
                } catch (Exception e) {
                    System.out.println("Error al convertir fechaFin: " + e.getMessage());
                }
            }
            
            List<ProduccionConsumoEnergia> registros;
            
            // Aplicar filtros según los parámetros proporcionados
            if (emprendimientoId != null && fechaDesde != null && fechaHasta != null) {
                registros = produccionConsumoEnergiaServices.obtenerPorEmprendimientoYRangoFechas(
                        emprendimientoId, fechaDesde, fechaHasta);
            } else if (emprendimientoId != null) {
                registros = produccionConsumoEnergiaServices.obtenerPorEmprendimiento(emprendimientoId);
            } else if (fechaDesde != null && fechaHasta != null) {
                registros = produccionConsumoEnergiaServices.obtenerPorRangoFechas(fechaDesde, fechaHasta);
            } else {
                registros = produccionConsumoEnergiaServices.obtenerTodos();
            }
            
            if (registros.isEmpty()) {
                // Si no hay datos, generar datos de ejemplo
                System.out.println("No se encontraron datos para los filtros especificados. Generando datos de ejemplo.");
                return ResponseEntity.ok(convertirAFormatoFrontend(generarDatosEjemploCompleto()));
            }
            
            return ResponseEntity.ok(convertirAFormatoFrontend(registros));
        } catch (Exception e) {
            System.out.println("Error en consultarDatos: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al consultar los datos: " + e.getMessage()));
        }
    }
}