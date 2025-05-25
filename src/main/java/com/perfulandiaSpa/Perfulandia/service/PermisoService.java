package com.perfulandiaSpa.Perfulandia.service;

import com.perfulandiaSpa.Perfulandia.dto.request.PermisoRequestDTO;
import com.perfulandiaSpa.Perfulandia.model.Permiso;
import com.perfulandiaSpa.Perfulandia.repository.PermisoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermisoService {
    @Autowired
    private PermisoRepository permisoRepository;

    public Permiso crearPermiso (PermisoRequestDTO permisoRequestDTO) {
        Permiso permiso = new Permiso();
        permiso.setNombrePermiso(permisoRequestDTO.getNombrePermiso());
        return permisoRepository.save(permiso);

    }
}
