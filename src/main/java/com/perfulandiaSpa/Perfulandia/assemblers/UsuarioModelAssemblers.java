package com.perfulandiaSpa.Perfulandia.assemblers;

import com.perfulandiaSpa.Perfulandia.controller.UsuarioController;
import com.perfulandiaSpa.Perfulandia.controller.UsuarioControllerV2;
import com.perfulandiaSpa.Perfulandia.dto.response.UsuarioDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UsuarioModelAssemblers implements RepresentationModelAssembler<UsuarioDTO, EntityModel<UsuarioDTO>> {
    @Override
    public EntityModel<UsuarioDTO> toModel(UsuarioDTO usuarioDTO){
        return EntityModel.of(usuarioDTO,
                linkTo(methodOn(UsuarioControllerV2.class).obtenerUsuarioPorId(usuarioDTO.getId())).withRel("buscarUsuarioPorId"),
                linkTo(methodOn(UsuarioControllerV2.class).listarUsuarios()).withRel("ListaUsuarios"));

    }

}
