package com.perfulandiaSpa.Perfulandia.dto.response;

import com.perfulandiaSpa.Perfulandia.model.Permiso;
import com.perfulandiaSpa.Perfulandia.model.Rol;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class RolDTO {
    private long id;
    private String tipoRol;
    private Set<String> permisosOtorgados;

    public RolDTO(Rol rol) {
        this.id = rol.getId();
        this.tipoRol = rol.getTipoRol();
        this.permisosOtorgados = new HashSet<>();
        for (Permiso permiso : rol.getPermisos()) {
            this.permisosOtorgados.add(permiso.getNombrePermiso());

        }
    }




}
