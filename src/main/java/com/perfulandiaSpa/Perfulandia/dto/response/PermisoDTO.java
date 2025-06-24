package com.perfulandiaSpa.Perfulandia.dto.response;
import com.perfulandiaSpa.Perfulandia.model.Permiso;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "DTO Permisos")
@Data
@AllArgsConstructor
@NoArgsConstructor

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
