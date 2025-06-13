package com.perfulandiaSpa.Perfulandia.controller;

import com.perfulandiaSpa.Perfulandia.dto.request.RolRequestDTO;
import com.perfulandiaSpa.Perfulandia.dto.response.RolDTO;
import com.perfulandiaSpa.Perfulandia.model.Rol;
import com.perfulandiaSpa.Perfulandia.service.RolService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
@Tag(name = "Roles", description = "Operaciones con los roles")


public class RolController {
    @Autowired
    private RolService rolService;

    @PostMapping("/{idUsuario}")
    @Operation(summary = "Crear un nuevo rol", description = "Permite crear un nuevo rol")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Rol creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud no disponible"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<RolDTO> crearRol(@RequestBody RolRequestDTO rolRequestDTO, @PathVariable Long idUsuario) {
        Rol rolNuevo =rolService.crearRol(rolRequestDTO,idUsuario);
        RolDTO rolDTO = new RolDTO(rolNuevo);
        return ResponseEntity.status(HttpStatus.CREATED).body(rolDTO);
    }

    @GetMapping
    @Operation(summary = "Obtener todos los roles", description = "Obtienes una lista con todos los roles creados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de roles generada correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<RolDTO>> listaRoles() {
        List<RolDTO> roles = rolService.listaRoles();
        return ResponseEntity.ok(roles);
    }
}
