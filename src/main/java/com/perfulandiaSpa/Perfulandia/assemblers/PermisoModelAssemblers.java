package com.perfulandiaSpa.Perfulandia.assemblers;
import com.perfulandiaSpa.Perfulandia.controller.PermisoControllerV2;
import com.perfulandiaSpa.Perfulandia.dto.response.PermisoDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
@Component
public class PermisoModelAssemblers implements RepresentationModelAssembler<PermisoDTO, EntityModel<PermisoDTO>> {
    @Override
    public EntityModel<PermisoDTO> toModel(PermisoDTO permisoDTO) {
        return EntityModel.of(permisoDTO,
                linkTo(methodOn(PermisoControllerV2.class).listaPermisos()).withRel("ListaPermisos"));
    }
}
