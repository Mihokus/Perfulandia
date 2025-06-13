package com.perfulandiaSpa.Perfulandia.dto.response;

import com.perfulandiaSpa.Perfulandia.model.Permiso;
import com.perfulandiaSpa.Perfulandia.model.Rol;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Schema(description = "DTO Roles")
public class RolDTO {
    @Schema(description = "ID de rol", example = "1")
    private long id;
    @Schema(description = "Nombre del rol", example = "ADMIN")
    private String tipoRol;
    @Schema(description = "Permisos asignados al rol", example = "[CREAR_USUARIO]")
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
