package com.perfulandiaSpa.Perfulandia.controller;

import com.perfulandiaSpa.Perfulandia.assemblers.UsuarioModelAssemblers;
import com.perfulandiaSpa.Perfulandia.dto.request.UsuarioRequestDTO;
import com.perfulandiaSpa.Perfulandia.dto.response.UsuarioDTO;
import com.perfulandiaSpa.Perfulandia.model.Usuario;
import com.perfulandiaSpa.Perfulandia.service.UsuarioService;
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
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
//http://localhost:8080/doc/swagger-ui/index.html//

@RequestMapping("/api/v2/usuarios")
@RestController
@Tag(name = "Usuarios", description = "Operaciones con las usuarios")
public class UsuarioControllerV2 {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioModelAssemblers assembler;

    @PostMapping(value = "/{idUsuario}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crear un nuevo usuario", description = "Permite crear un nuevo usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta o datos inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<?> crearUsuario(@io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                       description = "Datos del usuario a crear",
                                                       required = true,
                                                       content = @Content(mediaType ="application/json",
                                                               schema = @Schema(implementation = UsuarioRequestDTO.class),
                                                               examples = @ExampleObject(
                                                                       name = "Ejemplo del usuario",
                                                                       value = "{\"run\":\"20584564-9\"," +
                                                                               "\"nombre\":\"Sebastian\"," +
                                                                               "\"apellido\":\"Rodriguez\"," +
                                                                               "\"correo\":\"Sebatian503@gmail.com\"," +
                                                                               "\"fechaNacimiento\": \"2000-05-23\"," +
                                                                               "\"activo\":true," +
                                                                               "\"rolId\": 1}"

                                                               )
                                                       ))
                                               @RequestBody UsuarioRequestDTO usuarioRequestDTO,
                                               @Parameter(description = "ID del usuario que crea el nuevo usuario", example = "1")
                                               @PathVariable Long idUsuario)
    {
        try {
            Usuario usuario = usuarioService.crearUsuario(idUsuario, usuarioRequestDTO);
            UsuarioDTO usuarioDTO = new UsuarioDTO(usuario);
            EntityModel<UsuarioDTO> model = assembler.toModel(usuarioDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(model);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @GetMapping("/listarUsuario")
    @Operation(summary = "Obtener todos los usuarios", description = "Obtienes una lista con todos los usuarios creados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuarios generada correctamente",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UsuarioDTO.class))))
    })
    public ResponseEntity<List<EntityModel<UsuarioDTO>>> listarUsuarios() {
        List<UsuarioDTO> usuarios = usuarioService.listarUsuarios();
        List<EntityModel<UsuarioDTO>> modelos = usuarios.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(modelos);
    }

    @GetMapping(value = "/{idUsuario}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener un usuario por su Id", description = "Obtienes un usuario en específico ingresando su Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado correctamente",
                         content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<EntityModel<UsuarioDTO>> obtenerUsuarioPorId(
            @Parameter(description = "ID del usuario que desea buscar", example = "1")
            @PathVariable Long idUsuario) {
        try {
            UsuarioDTO dto = usuarioService.obtenerUsuarioPorId(idUsuario);
            EntityModel<UsuarioDTO> model = assembler.toModel(dto);
            return ResponseEntity.ok(model);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping(value = "/{idSolicitante}/{idEditar}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Editar usuario", description = "Permite editar las características de un usuario ingresando su Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "No se pudo procesar la solicitud", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "200", description = "Usuario editado correctamente", content = @Content(mediaType = "application/json"))
    })

    public ResponseEntity<?> editarUsuario(
            @RequestBody UsuarioRequestDTO usuarioRequestDTO,
            @PathVariable Long idSolicitante,
            @PathVariable Long idEditar) {
        try {
            UsuarioDTO dto = usuarioService.editarUsuario(idSolicitante, idEditar, usuarioRequestDTO);
            EntityModel<UsuarioDTO> model = assembler.toModel(dto);
            return ResponseEntity.ok(model);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{idSolicitante}/{id}")
    @Operation(summary = "Eliminar usuario", description = "Eliminas un usuario en específico ingresando su Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario eliminado correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<String> eliminarUsuario(
            @Parameter(description = "ID del usuario solicitante", example = "1") @PathVariable Long idSolicitante,
            @Parameter(description = "ID del usuario a eliminar", example = "2") @PathVariable Long id) {
        try {
            String mensaje = usuarioService.eliminarUsuario(id, idSolicitante);
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }




}
