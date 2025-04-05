package com.example.proyectotalentotech.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Servicio para la gestión de tokens JWT (JSON Web Token).
 * <p>
 * Esta clase proporciona funcionalidades para crear, validar y extraer información de tokens
 * JWT utilizados en la autenticación y autorización de usuarios. Utiliza la biblioteca JJWT
 * para la generación y procesamiento de tokens.
 * </p>
 * <p>
 * El servicio maneja las siguientes operaciones principales:
 * <ul>
 *   <li>Generación de tokens JWT para usuarios autenticados</li>
 *   <li>Validación de tokens JWT recibidos en solicitudes</li>
 *   <li>Extracción de información de reclamaciones (claims) de los tokens</li>
 *   <li>Verificación de la expiración de los tokens</li>
 * </ul>
 * </p>
 * 
 * @author Equipo RedComunitaria
 * @version 1.0
 * @since 2023-03-30
 */
@Service
public class JwtService {

    @Value("${app.security.jwt.secret-key:404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970}")
    private String secretKey;
    
    @Value("${app.security.jwt.expiration:86400000}")
    private long jwtExpiration;

    /**
     * Extrae el nombre de usuario (subject) de un token JWT.
     * 
     * @param token El token JWT del cual extraer el nombre de usuario
     * @return El nombre de usuario almacenado en el token
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extrae una reclamación específica del token JWT utilizando una función de resolución.
     * <p>
     * Este método genérico permite extraer cualquier tipo de reclamación del token
     * proporcionando una función que mapea las reclamaciones al tipo deseado.
     * </p>
     * 
     * @param <T> El tipo de dato que se espera extraer
     * @param token El token JWT del cual extraer la reclamación
     * @param claimsResolver La función para resolver la reclamación deseada
     * @return El valor de la reclamación extraída del token
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Genera un token JWT para un usuario sin reclamaciones adicionales.
     * 
     * @param userDetails Los detalles del usuario para el cual generar el token
     * @return Un token JWT firmado
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Genera un token JWT con reclamaciones adicionales para un usuario.
     * <p>
     * Este método permite incluir reclamaciones personalizadas en el token,
     * además de las reclamaciones estándar como el sujeto (username), la fecha
     * de emisión y la fecha de expiración.
     * </p>
     * 
     * @param extraClaims Mapa de reclamaciones adicionales a incluir en el token
     * @param userDetails Los detalles del usuario para el cual generar el token
     * @return Un token JWT firmado con las reclamaciones adicionales
     */
    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    /**
     * Construye un token JWT con las reclamaciones y tiempo de expiración especificados.
     * <p>
     * Este método privado es utilizado internamente para construir el token JWT
     * con todas las reclamaciones necesarias, establecer la fecha de emisión y expiración,
     * y firmarlo con la clave secreta.
     * </p>
     * 
     * @param extraClaims Mapa de reclamaciones adicionales a incluir en el token
     * @param userDetails Los detalles del usuario para el cual generar el token
     * @param expiration El tiempo de expiración del token en milisegundos
     * @return Un token JWT firmado
     */
    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Verifica si un token JWT es válido para un usuario específico.
     * <p>
     * Un token se considera válido si:
     * <ul>
     *   <li>El nombre de usuario en el token coincide con el nombre de usuario proporcionado</li>
     *   <li>El token no ha expirado</li>
     * </ul>
     * </p>
     * 
     * @param token El token JWT a validar
     * @param userDetails Los detalles del usuario para validar el token
     * @return true si el token es válido, false en caso contrario
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Verifica si un token JWT ha expirado.
     * 
     * @param token El token JWT a verificar
     * @return true si el token ha expirado, false en caso contrario
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extrae la fecha de expiración de un token JWT.
     * 
     * @param token El token JWT del cual extraer la fecha de expiración
     * @return La fecha de expiración del token
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extrae todas las reclamaciones de un token JWT.
     * <p>
     * Este método analiza el token JWT, verifica su firma utilizando la clave secreta,
     * y extrae todas las reclamaciones del cuerpo del token.
     * </p>
     * 
     * @param token El token JWT a analizar
     * @return Las reclamaciones contenidas en el token
     * @throws io.jsonwebtoken.JwtException Si el token es inválido o no puede ser analizado
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Obtiene la clave de firma para verificar y firmar tokens JWT.
     * <p>
     * Este método decodifica la clave secreta de base64 y la convierte en una
     * clave HMAC-SHA para ser utilizada en la firma y verificación de tokens.
     * </p>
     * 
     * @return La clave de firma
     */
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
} 