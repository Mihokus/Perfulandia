package com.perfulandiaSpa.Perfulandia.controller;

import com.perfulandiaSpa.Perfulandia.dto.request.PermisoRequestDTO;
import com.perfulandiaSpa.Perfulandia.dto.response.PermisoDTO;
import com.perfulandiaSpa.Perfulandia.model.Permiso;
import com.perfulandiaSpa.Perfulandia.service.PermisoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Permiso creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado o sin permisos"),
    })
    @Operation(summary = "Crear un nuevo permiso", description = "Permite crear un nuevo permiso")
    public ResponseEntity<?> crearPermiso(@RequestBody PermisoRequestDTO permisoRequestDTO,
                                          @Parameter(description = "ID del usuario que desea crear el permiso", example = "1")
                                          @PathVariable Long idUsuario) {
        try {
            PermisoDTO permisoDTO = permisoService.crearPermiso(permisoRequestDTO, idUsuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(permisoDTO);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado: " + ex.getMessage());
        }
    }

    @GetMapping
    @Operation(summary = "Obtener todos los permisos", description = "Obtienes una lista con todos los permisos creados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de permisos generada correctamente",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PermisoDTO.class))))
    })
    public ResponseEntity<List<PermisoDTO>> listaPermisos() {
        List<PermisoDTO> permisos = permisoService.listaPermiso();
        return ResponseEntity.ok(permisos);
    }


}
