package com.perfulandiaSpa.Perfulandia.dto.request;

import lombok.Data;

import java.util.Set;

@Data
public class RolRequestDTO {
    private String tipoRol;
    private Set<Long> permisoId;
}
