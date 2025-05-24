package com.perfulandiaSpa.Perfulandia.dto.request;

import lombok.Data;

import java.util.Date;

@Data
public class UsuarioRequestDTO {
    private long id;
    private String run;
    private String nombre;
    private String apellido;
    private String correo;
    private Date fechaNacimiento;
    private boolean activo;
    private Long roleId;
}
