package com.perfulandiaSpa.Perfulandia.controller;

import com.perfulandiaSpa.Perfulandia.dto.request.PermisoRequestDTO;
import com.perfulandiaSpa.Perfulandia.dto.response.PermisoDTO;
import com.perfulandiaSpa.Perfulandia.model.Permiso;
import com.perfulandiaSpa.Perfulandia.service.PermisoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/permisos")
@RestController
public class PermisoController {
    @Autowired
    private PermisoService permisoService;

    @PostMapping("/crearPermisos/{idUsuario}")
    public ResponseEntity<PermisoDTO> crearPermiso(@RequestBody PermisoRequestDTO permisoRequestDTO, @PathVariable Long idUsuario) {
        Permiso permiso = permisoService.crearPermiso(permisoRequestDTO, idUsuario);
        PermisoDTO permisoDTO = new PermisoDTO(permiso);
        return ResponseEntity.ok(permisoDTO);

    }


}
