package com.example.proyectotalentotech.model;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Data
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idusuarios;
    @Column(name = "email_user", nullable = false, unique = true, length = 255)
    private String emailUser;
    @Column(nullable = false, length = 255)
    private String password_user;
    @Column(nullable = false)
    private Boolean estado_user;
    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idroles")
    private Roles rol;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(rol.getNombreRol()));
    }

    @Override
    public String getPassword() {
        return password_user;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return estado_user;
    }
}

