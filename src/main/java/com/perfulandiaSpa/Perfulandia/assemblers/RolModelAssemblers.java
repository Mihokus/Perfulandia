package com.perfulandiaSpa.Perfulandia.assemblers;

import com.perfulandiaSpa.Perfulandia.controller.RolControllerV2;
import com.perfulandiaSpa.Perfulandia.dto.response.RolDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class RolModelAssemblers implements RepresentationModelAssembler<RolDTO, EntityModel<RolDTO>> {
    @Override
    public EntityModel<RolDTO> toModel(RolDTO rolDTO) {
        return EntityModel.of(rolDTO,
                linkTo(methodOn(RolControllerV2.class).listaRoles()).withRel("ListaRoles"));
    }
}
