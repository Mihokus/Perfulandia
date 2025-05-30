package com.perfulandiaSpa.Perfulandia.repository;

import com.perfulandiaSpa.Perfulandia.dto.response.UsuarioDTO;
import com.perfulandiaSpa.Perfulandia.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    @Query("select new com.perfulandiaSpa.Perfulandia.dto.response.UsuarioDTO(u) " +
            "from Usuario u " +
            "where u.id = :idUsuario")
    Optional<UsuarioDTO> findUsuarioDTOById(@Param("idUsuario") Long idUsuario);
}
