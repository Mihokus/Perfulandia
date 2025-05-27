package com.perfulandiaSpa.Perfulandia.repository;

import com.perfulandiaSpa.Perfulandia.dto.response.UsuarioDTO;
import com.perfulandiaSpa.Perfulandia.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    @Query("SELECT DISTINCT NEW com.perfulandiaSpa.Perfulandia.dto.response.UsuarioDTO(u.id, u.nombre, u.apellido)" +
    "FROM Usuario u")
    List<UsuarioDTO> findAllUsuarios();

}
