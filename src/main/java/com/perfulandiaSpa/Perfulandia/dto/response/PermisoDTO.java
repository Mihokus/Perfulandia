package com.perfulandiaSpa.Perfulandia.dto.response;

import com.perfulandiaSpa.Perfulandia.model.Permiso;
import lombok.Data;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties;

@Data
public class PermisoDTO {
    private long id;
    private String nombrePermiso;

    public PermisoDTO(Permiso permiso) {
        this.id = permiso.getId();
        this.nombrePermiso = permiso.getNombrePermiso();
    }
}
