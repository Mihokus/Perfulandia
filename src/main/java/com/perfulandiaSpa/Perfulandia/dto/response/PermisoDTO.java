package com.perfulandiaSpa.Perfulandia.dto.response;

import com.perfulandiaSpa.Perfulandia.model.Permiso;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
@Schema(description = "DTO Permisos")
@Data
public class PermisoDTO {
    @Schema(description = "Id del permiso", example = "1")
    private long id;
    @Schema(description = "Nombre del permiso", example = "CREAR_USUARIO")
    private String nombrePermiso;

    public PermisoDTO(Permiso permiso) {
        this.id = permiso.getId();
        this.nombrePermiso = permiso.getNombrePermiso();
    }
}
