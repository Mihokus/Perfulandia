package com.perfulandiaSpa.Perfulandia.controller;

import com.perfulandiaSpa.Perfulandia.dto.request.RolRequestDTO;
import com.perfulandiaSpa.Perfulandia.dto.response.RolDTO;
import com.perfulandiaSpa.Perfulandia.model.Rol;
import com.perfulandiaSpa.Perfulandia.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")

public class RolController {
    @Autowired
    private RolService rolService;

    @PostMapping("/{idUsuario}")
    public ResponseEntity<RolDTO> crearRol(@RequestBody RolRequestDTO rolRequestDTO, @PathVariable Long idUsuario) {
        Rol rolNuevo =rolService.crearRol(rolRequestDTO,idUsuario);
        RolDTO rolDTO = new RolDTO(rolNuevo);
        return ResponseEntity.status(HttpStatus.CREATED).body(rolDTO);
    }
    @GetMapping
    public ResponseEntity<List<RolDTO>> listaRoles() {
        List<RolDTO> roles = rolService.listaRoles();
        return ResponseEntity.ok(roles);
    }

}
