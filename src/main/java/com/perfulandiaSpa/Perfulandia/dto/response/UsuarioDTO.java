package com.perfulandiaSpa.Perfulandia.dto.response;

import com.perfulandiaSpa.Perfulandia.model.Usuario;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;
@NoArgsConstructor
@Data
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


