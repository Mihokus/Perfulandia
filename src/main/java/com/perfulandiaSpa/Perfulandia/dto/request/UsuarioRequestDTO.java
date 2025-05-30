package com.perfulandiaSpa.Perfulandia.dto.request;

import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class UsuarioRequestDTO {
    private long id;
    private String run;
    private String nombre;
    private String apellido;
    private String correo;
    private Date fechaNacimiento;
    private boolean activo;
    private Long rolId;
    private Set<Long> permiso;

}
