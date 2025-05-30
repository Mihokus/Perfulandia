package com.perfulandiaSpa.Perfulandia.service;

import com.perfulandiaSpa.Perfulandia.dto.request.RolRequestDTO;
import com.perfulandiaSpa.Perfulandia.model.Permiso;
import com.perfulandiaSpa.Perfulandia.model.Rol;
import com.perfulandiaSpa.Perfulandia.model.Usuario;
import com.perfulandiaSpa.Perfulandia.repository.PermisoRepository;
import com.perfulandiaSpa.Perfulandia.repository.RolRepository;
import com.perfulandiaSpa.Perfulandia.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
@Service

public class RolService {
    @Autowired
    PermisoRepository permisoRepository;
    @Autowired
    RolRepository rolRepository;
    @Autowired
    UsuarioRepository usuarioRepository;

    public void validacionDePermisosCrearRol(Long idUsuario) {
        if (usuarioRepository.count() == 0){
            return;
        }
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
        if (!usuario.isActivo()) {
            throw new EntityNotFoundException("El usuario está desactivado y no puede realizar acciones");
        }
        String tipoRol = usuario.getRol().getTipoRol();
        if ("ADMIN".equalsIgnoreCase(tipoRol)) {
            return;
        }
        for (Permiso permiso :usuario.getRol().getPermisos()){
            if("CREAR_ROL".equalsIgnoreCase(permiso.getNombrePermiso())){
                return;
            }
        }
        throw new EntityNotFoundException("El usuario no tiene los permisos");
    }
    public Rol crearRol(RolRequestDTO rolRequestDTO, Long idUsuario) {
        validacionDePermisosCrearRol(idUsuario);
        Set<Permiso> permisos = new HashSet<>();
        for (Long permisoid : rolRequestDTO.getPermisoId()){
            Optional<Permiso> permiso = permisoRepository.findById(permisoid);
            permiso.ifPresent(permisos::add);
        }
        Rol rol = new Rol();
        rol.setTipoRol(rolRequestDTO.getTipoRol());
        rol.setPermisos(permisos);
        return rolRepository.save(rol);
        }


}
