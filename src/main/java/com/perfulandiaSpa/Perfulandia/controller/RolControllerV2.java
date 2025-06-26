package com.perfulandiaSpa.Perfulandia.controller;

import com.perfulandiaSpa.Perfulandia.assemblers.RolModelAssemblers;
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
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/roles")
@Tag(name = "Roles", description = "Operaciones con los roles")
public class RolControllerV2 {
    @Autowired
    private RolService rolService;

    @Autowired
    private RolModelAssemblers assembler;

    @PostMapping(value = "/{idUsuario}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crear un nuevo rol", description = "Permite crear un nuevo rol")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Rol creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud invalida"),
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
        EntityModel<RolDTO> model = assembler.toModel(rolDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(rolDTO);
    }
    @GetMapping
    @Operation(summary = "Obtener todos los roles", description = "Obtienes una lista con todos los roles creados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de roles generada correctamente",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = RolDTO.class))))})
    public ResponseEntity<List<EntityModel<RolDTO>>> listaRoles() {
        List<RolDTO> roles = rolService.listaRoles();
        List<EntityModel<RolDTO>> rolModels = roles.stream()
                .map(assembler::toModel)
                .toList();
        return ResponseEntity.ok(rolModels);
    }




}
