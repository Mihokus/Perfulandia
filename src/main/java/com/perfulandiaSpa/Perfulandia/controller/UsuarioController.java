package com.perfulandiaSpa.Perfulandia.controller;

import com.perfulandiaSpa.Perfulandia.dto.request.UsuarioRequestDTO;
import com.perfulandiaSpa.Perfulandia.dto.response.UsuarioDTO;
import com.perfulandiaSpa.Perfulandia.model.Usuario;
import com.perfulandiaSpa.Perfulandia.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequestMapping("/api/v1/usuarios")
@RestController

public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;
    /*
      "fechaNacimiento": "2001-05-29T02:42:38.881+00:00",
    */


    @PostMapping("/{idUsuario}")
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
    public List<UsuarioDTO> listarUsuarios() {
        return usuarioService.listarUsuarios();
    }

    @GetMapping("/{idUsuario}")
    public ResponseEntity<UsuarioDTO> obtenerUsuarioPorId(@PathVariable Long idUsuario) {
        UsuarioDTO usuarioDTO = usuarioService.obtenerUsuarioPorId(idUsuario);
        return ResponseEntity.ok(usuarioDTO);
    }
    @PutMapping("/{idSolicitante}/{idEditar}")
    public ResponseEntity<?> editarUsuario(@RequestBody UsuarioRequestDTO usuarioRequestDTO, @PathVariable Long idSolicitante, @PathVariable Long idEditar ) {
        try {
            UsuarioDTO usuarioDTO = usuarioService.editarUsuario(idSolicitante,idEditar, usuarioRequestDTO);
            return ResponseEntity.ok(usuarioDTO);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }





    @DeleteMapping("/{idSolicitante}/{id}")
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
