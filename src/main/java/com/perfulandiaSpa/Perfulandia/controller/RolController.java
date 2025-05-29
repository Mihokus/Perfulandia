package com.perfulandiaSpa.Perfulandia.controller;

import com.perfulandiaSpa.Perfulandia.dto.request.RolRequestDTO;
import com.perfulandiaSpa.Perfulandia.dto.response.RolDTO;
import com.perfulandiaSpa.Perfulandia.model.Rol;
import com.perfulandiaSpa.Perfulandia.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/roles")

public class RolController {
    @Autowired
    private RolService rolService;

    @PostMapping
    public ResponseEntity<RolDTO> crearRol(@RequestBody RolRequestDTO rolRequestDTO) {
        Rol rolNuevo =rolService.crearRol(rolRequestDTO);
        RolDTO rolDTO = new RolDTO(rolNuevo);
        return ResponseEntity.ok(rolDTO);
    }
}
