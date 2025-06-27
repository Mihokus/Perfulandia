package com.perfulandiaSpa.Perfulandia.controller;

import com.perfulandiaSpa.Perfulandia.dto.request.PermisoRequestDTO;
import com.perfulandiaSpa.Perfulandia.dto.request.RolRequestDTO;
import com.perfulandiaSpa.Perfulandia.dto.response.RolDTO;
import com.perfulandiaSpa.Perfulandia.model.Rol;
import com.perfulandiaSpa.Perfulandia.service.RolService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
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
            @ApiResponse(responseCode = "201", description = "Rol creado correctamente", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Solicitud invalida", content = @Content(mediaType = "application/json")),
    })
    public ResponseEntity<RolDTO> crearRol(@io.swagger.v3.oas.annotations.parameters.RequestBody(
                                           description = "Datos del rol a crear",
                                           required = true,
                                           content = @Content(mediaType = "application/json",
                                           schema = @Schema(implementation = RolRequestDTO.class),
                                           examples = @ExampleObject(
                                           name = "Ejemplo Rol",
                                           value = "{\"nombrePermiso\":\"CREAR_USUARIO\","+
                                                   "\"permisoId\":\"1}")))
                                           @RequestBody RolRequestDTO rolRequestDTO,
                                           @Parameter(description = "ID del usuario que desea crear el rol", example = "1")
                                           @PathVariable Long idUsuario) {

        Rol rolNuevo =rolService.crearRol(rolRequestDTO,idUsuario);
        RolDTO rolDTO = new RolDTO(rolNuevo);
        return ResponseEntity.status(HttpStatus.CREATED).body(rolDTO);
    }

    @GetMapping
    @Operation(summary = "Obtener todos los roles", description = "Obtienes una lista con todos los roles creados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de roles generada correctamente",
                    content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = RolDTO.class))))})
    public ResponseEntity<List<RolDTO>> listaRoles() {
        List<RolDTO> roles = rolService.listaRoles();
        return ResponseEntity.ok(roles);
    }
}
