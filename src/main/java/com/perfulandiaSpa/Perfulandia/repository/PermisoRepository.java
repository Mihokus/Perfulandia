package com.perfulandiaSpa.Perfulandia.repository;

import com.perfulandiaSpa.Perfulandia.dto.response.PermisoDTO;
import com.perfulandiaSpa.Perfulandia.dto.response.RolDTO;
import com.perfulandiaSpa.Perfulandia.model.Permiso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PermisoRepository extends JpaRepository<Permiso, Long> {
    @Query("select new com.perfulandiaSpa.Perfulandia.dto.response.PermisoDTO(p)"
    +" from Permiso p")
    List<PermisoDTO> listaPermisos();
}
