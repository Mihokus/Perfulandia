package com.perfulandiaSpa.Perfulandia.controller;

import com.perfulandiaSpa.Perfulandia.dto.request.PermisoRequestDTO;
import com.perfulandiaSpa.Perfulandia.model.Permiso;
import com.perfulandiaSpa.Perfulandia.service.PermisoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/permiso")
@RestController
public class PermisoController {
    @Autowired
    private PermisoService permisoService;

    @PostMapping
    public ResponseEntity<Permiso> crearPermiso(@RequestBody PermisoRequestDTO permisoRequestDTO) {
        Permiso permiso = permisoService.crearPermiso(permisoRequestDTO);
        return ResponseEntity.ok(permiso);

    }


}
