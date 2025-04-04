package com.example.proyectotalentotech.repository;

import com.example.proyectotalentotech.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByEmailUser(String email);
    Optional<Usuario> findByUsername(String username);
}

