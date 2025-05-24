package com.perfulandiaSpa.Perfulandia.dto.response;

import com.perfulandiaSpa.Perfulandia.model.Usuario;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class UsuarioDTO {
    private long id;
    private String run;
    private String nombre;
    private String apellido;
    private String email;
    private Date fechaNacimiento;
    private boolean activo;
    private String rol;
    private Set<String> permiso;



}
