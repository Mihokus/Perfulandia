package com.perfulandiaSpa.Perfulandia.dto.response;

import lombok.Data;

import java.util.Set;

@Data
public class RolDTO {
    private long id;
    private String tipoRol;
    private Set<String> permisosOrtogados;
}
