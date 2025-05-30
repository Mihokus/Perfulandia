package com.perfulandiaSpa.Perfulandia.repository;

import com.perfulandiaSpa.Perfulandia.dto.response.RolDTO;
import com.perfulandiaSpa.Perfulandia.model.Rol;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RolRepository extends CrudRepository<Rol, Long> {
    @Query("select new com.perfulandiaSpa.Perfulandia.dto.response.RolDTO(r)"+
    "from Rol r")
    List<RolDTO> buscarRoles();

}
