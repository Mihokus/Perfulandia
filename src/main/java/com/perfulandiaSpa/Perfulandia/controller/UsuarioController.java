package com.perfulandiaSpa.Perfulandia.controller;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;



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
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta o datos inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<String> crearUsuario(@io.swagger.v3.oas.annotations.parameters.RequestBody(
                                               description = "Datos del usuario a crear",
                                               required = true,
                                               content = @Content(mediaType ="application/json",
                                               schema = @Schema(implementation = UsuarioRequestDTO.class),
                                               examples = @ExampleObject(
                                               name = "Ejemplo del usuario",
                                               value = "{\"Run\":\"20584564-9\"," +
                                                       "\"Nombre\":\"Sebastian\"," +
                                                       "\"Apellido\":\"Rodriguez\"," +
                                                       "\"Correo\":\"Sebatian503@gmail.com\"," +
                                                       "\"FechaNacimiento\": \"2000-05-23\"," +
                                                       "\"Activo\":true," +
                                                       "\"RolId\": 1}"

                                               )
                                                       ))
                                               @RequestBody UsuarioRequestDTO usuarioRequestDTO,
                                               @Parameter(description = "ID del usuario que crea el nuevo usuario", example = "1")
                                               @PathVariable Long idUsuario)
    {
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
            @ApiResponse(responseCode = "200", description = "Lista de usuarios generada correctamente",
                         content = @Content(mediaType = "application/json",
                         array = @ArraySchema(schema = @Schema(implementation = UsuarioDTO.class))))



    })
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios() {
        List<UsuarioDTO> usuarios = usuarioService.listarUsuarios();
        return ResponseEntity.ok(usuarios);
    }


    @GetMapping("/{idUsuario}")
    @Operation(summary = "Obtener un usuario por su Id", description = "Obtienes un usuario en específico ingresando su Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Usuario encontrado correctamente",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "EjemploUsuario",
                                    value = "{\"id\":\"1\","+
                                            "\"nombre\":\"Juan\"," +
                                            "\"apellido\":\"Perez\"," +
                                            "\"correo\":\"juan.perez@mail.com\"," +
                                            "\"activo\":true," +
                                            "\"rolId\":1}"
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
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
            @ApiResponse(responseCode = "200",
                         description = "Usuario eliminado correctamente",
                         content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")

    })
    public ResponseEntity<String> eliminarUsuario(@Parameter(description = "ID del usuario a eliminar", example = "2")
                                                  @PathVariable Long id,
                                                  @Parameter(description = "ID del usuario solicitante que realiza desea realizar la eliminacion", example = "1") @PathVariable Long idSolicitante) {
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
