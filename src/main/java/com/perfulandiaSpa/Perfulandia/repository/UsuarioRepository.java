package com.perfulandiaSpa.Perfulandia.repository;

import com.perfulandiaSpa.Perfulandia.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
