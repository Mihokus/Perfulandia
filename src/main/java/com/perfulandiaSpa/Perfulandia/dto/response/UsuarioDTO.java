package com.perfulandiaSpa.Perfulandia.dto.response;

import com.perfulandiaSpa.Perfulandia.model.Permiso;
import com.perfulandiaSpa.Perfulandia.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
@NoArgsConstructor
@Data
@AllArgsConstructor
public class UsuarioDTO {
    private long id;
    private String run;
    private String nombre;
    private String apellido;
    private String correo;
    private Date fechaNacimiento;
    private boolean activo;
    private String rol;
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


