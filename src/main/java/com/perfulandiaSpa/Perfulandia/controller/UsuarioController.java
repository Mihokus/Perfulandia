package com.perfulandiaSpa.Perfulandia.controller;

import com.perfulandiaSpa.Perfulandia.dto.request.UsuarioRequestDTO;
import com.perfulandiaSpa.Perfulandia.dto.response.UsuarioDTO;
import com.perfulandiaSpa.Perfulandia.model.Usuario;
import com.perfulandiaSpa.Perfulandia.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequestMapping("/api/v1/usuarios")
@RestController
@Tag(name = "Usuarios", description = "Operaciones con las usuarios")


public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;


    @PostMapping("/{idUsuario}")
    @Operation(summary = "Crear un nuevo usuario", description = "Permite crear un nuevo usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta o datos inválidos")
    })
    public ResponseEntity<String> crearUsuario(@RequestBody UsuarioRequestDTO usuarioRequestDTO,
                                               @Parameter(description = "ID del usuario que crea el nuevo usuario", example = "1")
                                               @PathVariable Long idUsuario) {
        try {
            Usuario usuario = usuarioService.crearUsuario(idUsuario,usuarioRequestDTO );
            return ResponseEntity.status(HttpStatus.CREATED).body("usuario creado correctamente");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    @GetMapping("/listarUsuario")
    @Operation(summary = "Obtener todos los usuarios", description = "Obtienes una lista con todos los usuarios creados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuarios generada correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public List<UsuarioDTO> listarUsuarios() {
        return usuarioService.listarUsuarios();
    }


    @GetMapping("/{idUsuario}")
    @Operation(summary = "Obtener un usuario por su Id", description = "Obtienes un usuario en específico ingresando su Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<UsuarioDTO> obtenerUsuarioPorId(@Parameter(description = "ID del usuario que desea buscar", example = "1")
                                                          @PathVariable Long idUsuario) {
        try {
            UsuarioDTO usuarioDTO = usuarioService.obtenerUsuarioPorId(idUsuario);
            return ResponseEntity.ok(usuarioDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @DeleteMapping("/{idSolicitante}/{id}")
    @Operation(summary = "Eliminar usuario", description = "Eliminas un usuario en especifico ingresando su Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario eliminado correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<String> eliminarUsuario(@Parameter(description = "ID del usuario a eliminar", example = "2")
                                                  @PathVariable Long id,
                                                  @Parameter(description = "ID del usuario solicitante que realiza desea realizar la eliminacion", example = "1")
                                                  @PathVariable Long idSolicitante) {
        String mensaje = "";
        try {
            mensaje = usuarioService.eliminarUsuario(id,idSolicitante);
            return ResponseEntity.status(HttpStatus.OK).body(mensaje);
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
