package com.perfulandiaSpa.Perfulandia.service;

import com.perfulandiaSpa.Perfulandia.dto.request.UsuarioRequestDTO;
import com.perfulandiaSpa.Perfulandia.dto.response.UsuarioDTO;
import com.perfulandiaSpa.Perfulandia.model.Permiso;
import com.perfulandiaSpa.Perfulandia.model.Rol;
import com.perfulandiaSpa.Perfulandia.model.Usuario;
import com.perfulandiaSpa.Perfulandia.repository.RolRepository;
import com.perfulandiaSpa.Perfulandia.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@Transactional

public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;
    public Usuario crearUsuario(UsuarioRequestDTO usuarioRequestDTO) {
        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioRequestDTO.getNombre());
        usuario.setApellido(usuarioRequestDTO.getApellido());
        usuario.setRun(usuarioRequestDTO.getRun());
        usuario.setCorreo(usuarioRequestDTO.getCorreo());
        usuario.setFechaNacimiento(usuarioRequestDTO.getFechaNacimiento());
        usuario.setActivo(usuarioRequestDTO.isActivo());
        Optional<Rol> rol = rolRepository.findById(usuarioRequestDTO.getRolId());
        if (rol.isEmpty()){
            throw new EntityNotFoundException("Rol no encontrado");
        }
        usuario.setRol(rol.get());
        return usuarioRepository.save(usuario);
    }

    public List<UsuarioDTO> listarUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        List<UsuarioDTO> usuarioDTOS = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            UsuarioDTO usuarioDTO = new UsuarioDTO();
            usuarioDTO.setId(usuario.getId());
            usuarioDTO.setRun(usuario.getRun());
            usuarioDTO.setNombre(usuario.getNombre());
            usuarioDTO.setApellido(usuario.getApellido());
            usuarioDTO.setCorreo(usuario.getCorreo());
            usuarioDTO.setFechaNacimiento(usuario.getFechaNacimiento());
            usuarioDTO.setActivo(usuario.isActivo());
            usuarioDTO.setRol(usuario.getRol().getTipoRol());
            Set<String> permisos = new HashSet<>();
            for (Permiso permiso : usuario.getRol().getPermisos()) {
                permisos.add(permiso.getNombrePermiso());
            }
            usuarioDTO.setPermiso(permisos);
            usuarioDTOS.add(usuarioDTO);
        }
        return usuarioDTOS;
    }
}
