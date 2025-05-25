package com.perfulandiaSpa.Perfulandia.service;

import com.perfulandiaSpa.Perfulandia.dto.request.RolRequestDTO;
import com.perfulandiaSpa.Perfulandia.model.Permiso;
import com.perfulandiaSpa.Perfulandia.model.Rol;
import com.perfulandiaSpa.Perfulandia.repository.PermisoRepository;
import com.perfulandiaSpa.Perfulandia.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
@Service

public class RolService {
    @Autowired
    RolRepository rolRepository;

    @Autowired
    PermisoRepository permisoRepository;

    public Rol crearRol(RolRequestDTO rolRequestDTO) {
        Set<Permiso> permisos = new HashSet<>();

        for (Long permisoid : rolRequestDTO.getPermisoId()){
            Optional<Permiso> permiso = permisoRepository.findById(permisoid);
            permiso.ifPresent(permisos::add);
        }
        Rol rol = new Rol();
        rol.setTipoRol(rolRequestDTO.getTipoRol());
        rol.setPermisos(permisos);
        return rolRepository.save(rol);
        }


}
