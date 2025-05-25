package com.perfulandiaSpa.Perfulandia.controller;

import com.perfulandiaSpa.Perfulandia.dto.request.RolRequestDTO;
import com.perfulandiaSpa.Perfulandia.model.Rol;
import com.perfulandiaSpa.Perfulandia.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/rol")

public class RolController {
    @Autowired
    private RolService rolService;

    @PostMapping
    public ResponseEntity<Rol> crearRol(@RequestBody RolRequestDTO rolRequestDTO) {
        Rol rolNuevo =rolService.crearRol(rolRequestDTO);
        return ResponseEntity.ok(rolNuevo);
    }
}
