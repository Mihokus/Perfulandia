package com.perfulandiaSpa.Perfulandia.controller;

import com.perfulandiaSpa.Perfulandia.assemblers.PermisoModelAssemblers;
import com.perfulandiaSpa.Perfulandia.dto.request.PermisoRequestDTO;
import com.perfulandiaSpa.Perfulandia.dto.response.PermisoDTO;
import com.perfulandiaSpa.Perfulandia.model.Permiso;
import com.perfulandiaSpa.Perfulandia.service.PermisoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v2/permisos")
public class PermisoControllerV2 {
    @Autowired
    private PermisoService permisoService;

    @Autowired
    private PermisoModelAssemblers assembler;

    @PostMapping(value = "/{idUsuario}", produces = MediaTypes.HAL_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Permiso creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud invalida"),
    })
    @Operation(summary = "Crear un nuevo permiso", description = "Permite crear un nuevo permiso")
    public ResponseEntity<EntityModel<PermisoDTO>> crearPermiso(@RequestBody PermisoRequestDTO permisoRequestDTO,
                                                                @PathVariable Long idUsuario) {
        Permiso permiso = permisoService.crearPermiso(permisoRequestDTO, idUsuario);
        PermisoDTO permisoDTO = new PermisoDTO(permiso);
        return ResponseEntity.created(linkTo(methodOn(PermisoControllerV2.class).listaPermisos()).toUri())
                .body(assembler.toModel(permisoDTO));
    }

    @GetMapping
    @Operation(summary = "Obtener todos los permisos", description = "Obtienes una lista con todos los permisos creados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de permisos generada correctamente",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PermisoDTO.class))))
    })
    public ResponseEntity<List<EntityModel<PermisoDTO>>> listaPermisos() {
        List<PermisoDTO> permisos = permisoService.listaPermiso();
        List<EntityModel<PermisoDTO>> permisosModel = permisos.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(permisosModel);
    }
}
