package com.perfulandiaSpa.Perfulandia.dto.response;

import com.perfulandiaSpa.Perfulandia.model.Permiso;
import com.perfulandiaSpa.Perfulandia.model.Usuario;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
@NoArgsConstructor
@Data
@AllArgsConstructor
@Schema(description = "DTO Usuarios")
public class UsuarioDTO {
    @Schema(description = "ID de usuario", example = "1")
    private long id;
    @Schema(description = "Run del usuario", example = "10205989-3")
    private String run;
    @Schema(description = "Nombre del usuario", example = "Pedro")
    private String nombre;
    @Schema(description = "Apellido del usuario", example = "Rojas")
    private String apellido;
    @Schema(description = "Correo electronico del usuario", example = "Pepe703@gmail.com")
    private String correo;
    @Schema(description = "Fecha nacimiento del usuario", example = "1995-06-13")
    private Date fechaNacimiento;
    @Schema(description = "Indica si el usuario esta activo", example = "true")
    private boolean activo;
    @Schema(description = "Rol asignado al usuario", example = "EMPLEADO")
    private String rol;
    @Schema(description = "Permisos asociados al rol del usuario", example = "[CREAR_USUARIO]")
    private Set<String> permiso;

    public UsuarioDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.run = usuario.getRun();
        this.nombre = usuario.getNombre();
        this.apellido = usuario.getApellido();
        this.correo = usuario.getCorreo();
        this.fechaNacimiento = usuario.getFechaNacimiento();
        this.activo = usuario.isActivo();
        this.rol = usuario.getRol().getTipoRol();
        Set<String> permisos = new HashSet<>();
        for (Permiso permiso: usuario.getRol().getPermisos()) {
            permisos.add(permiso.getNombrePermiso());
        }
        this.permiso = permisos;

}

    public UsuarioDTO(Long id, String run, String nombre, String apellido, String correo, Date fechaNacimiento, boolean activo, String rol ,  Set<String>permiso){
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.fechaNacimiento = fechaNacimiento;
        this.activo = activo;
        this.rol = rol;
        this.permiso = permiso;
    }
}


