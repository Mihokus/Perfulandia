package com.perfulandiaSpa.Perfulandia.controller;

import com.perfulandiaSpa.Perfulandia.dto.request.UsuarioRequestDTO;
import com.perfulandiaSpa.Perfulandia.dto.response.UsuarioDTO;
import com.perfulandiaSpa.Perfulandia.model.Usuario;
import com.perfulandiaSpa.Perfulandia.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
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
    public ResponseEntity<String> crearUsuario(@RequestBody UsuarioRequestDTO usuarioRequestDTO, @PathVariable Long idUsuario) {
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
    public List<UsuarioDTO> listarUsuarios() {
        return usuarioService.listarUsuarios();
    }

    @GetMapping("/{idUsuario}")
    @Operation(summary = "Obtener un usuario por su Id", description = "Obtienes un usuario en especifico ingresando su Id")
    public ResponseEntity<UsuarioDTO> obtenerUsuarioPorId(@PathVariable Long idUsuario) {
        UsuarioDTO usuarioDTO = usuarioService.obtenerUsuarioPorId(idUsuario);
        return ResponseEntity.ok(usuarioDTO);
    }

    @PutMapping("/{idSolicitante}/{idEditar}")
    @Operation(summary = "Editar usuario", description = "Permite editar las caracteristicas de un usuario en especifico ingresando su Id")
    public ResponseEntity<?> editarUsuario(@RequestBody UsuarioRequestDTO usuarioRequestDTO, @PathVariable Long idSolicitante, @PathVariable Long idEditar ) {
        try {
            UsuarioDTO usuarioDTO = usuarioService.editarUsuario(idSolicitante,idEditar, usuarioRequestDTO);
            return ResponseEntity.ok(usuarioDTO);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{idSolicitante}/{id}")
    @Operation(summary = "Eliminar usuario", description = "Eliminas un usuario en especifico ingresando su Id")
    public ResponseEntity<String> eliminarUsuario(@PathVariable Long id, @PathVariable Long idSolicitante) {
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
