package com.perfulandiaSpa.Perfulandia.controller;

import com.perfulandiaSpa.Perfulandia.dto.request.PermisoRequestDTO;
import com.perfulandiaSpa.Perfulandia.dto.response.PermisoDTO;
import com.perfulandiaSpa.Perfulandia.model.Permiso;
import com.perfulandiaSpa.Perfulandia.service.PermisoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/permisos")
@RestController
@Tag(name = "Permisos", description = "Operaciones con los permisos")
public class PermisoController {
    @Autowired
    private PermisoService permisoService;

    @PostMapping("/{idUsuario}")
    @Operation(summary = "Crear un nuevo permiso", description = "Permite crear un nuevo permiso")
    public ResponseEntity<PermisoDTO> crearPermiso(@RequestBody PermisoRequestDTO permisoRequestDTO, @PathVariable Long idUsuario) {
        Permiso permiso = permisoService.crearPermiso(permisoRequestDTO, idUsuario);
        PermisoDTO permisoDTO = new PermisoDTO(permiso);
        return ResponseEntity.ok(permisoDTO);
    }

    @GetMapping
    @Operation(summary = "Obtener todos los permisos", description = "Obtienes una lista con todos los permisos creados")
    public ResponseEntity<List<PermisoDTO>> listaPermisos() {
        List<PermisoDTO> permisos = permisoService.listaPermiso();
        return ResponseEntity.ok(permisos);
    }
}
