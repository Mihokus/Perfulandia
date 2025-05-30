package com.perfulandiaSpa.Perfulandia.service;

import com.perfulandiaSpa.Perfulandia.dto.request.UsuarioRequestDTO;
import com.perfulandiaSpa.Perfulandia.dto.response.UsuarioDTO;
import com.perfulandiaSpa.Perfulandia.model.Permiso;
import com.perfulandiaSpa.Perfulandia.model.Rol;
import com.perfulandiaSpa.Perfulandia.model.Usuario;
import com.perfulandiaSpa.Perfulandia.repository.PermisoRepository;
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

    @Autowired
    private PermisoRepository permisoRepository;

    public void validacionDePermisos(Long idUsuario) {
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
            if("CREAR_USUARIO".equalsIgnoreCase(permiso.getNombrePermiso())){
                return;
            }
        }
        throw new EntityNotFoundException("El usuario no tiene los permisos");
    }
    public Usuario crearUsuario(Long idUsuario,UsuarioRequestDTO usuarioRequestDTO) {
        validacionDePermisos(idUsuario);
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


    private void validarPermisoEliminarUsuario(Long idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new EntityNotFoundException("Usuario solicitante no encontrado"));
        if (!usuario.isActivo()) {
            throw new EntityNotFoundException("El usuario está desactivado y no puede realizar acciones");
        }
        String tipoRol = usuario.getRol().getTipoRol();
        if ("ADMIN".equalsIgnoreCase(tipoRol)) {
            return;
        }
        for (Permiso permiso : usuario.getRol().getPermisos()){
            if ("ELIMINAR_USUARIO".equalsIgnoreCase(permiso.getNombrePermiso())){
                return;
            }
        }
        throw new EntityNotFoundException("El usuario no tiene los permisos para eliminar usuarios");
    }


    public String eliminarUsuario(Long idUsuario, Long idSolicitante) {
        validarPermisoEliminarUsuario(idSolicitante);
        usuarioRepository.deleteById(idUsuario);
        return "Usuario eliminado";
    }


    private void validarPermisoEditar(Long idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new EntityNotFoundException("Usuario solicitante no encontrado"));
        if (!usuario.isActivo()) {
            throw new EntityNotFoundException("El usuario está desactivado y no puede realizar acciones");
        }
        String tipoRol = usuario.getRol().getTipoRol();
        if ("ADMIN".equalsIgnoreCase(tipoRol)) {
            return;
        }
        for (Permiso permiso : usuario.getRol().getPermisos()){
            if ("EDITAR_USUARIO".equalsIgnoreCase(permiso.getNombrePermiso())){
                return;
            }
        }
        throw new EntityNotFoundException("El usuario no tiene los permisos para editar usuarios");
    }

    public UsuarioDTO editarUsuario(Long idSolicitante, Long idEditar, UsuarioRequestDTO usuarioRequestDTO) {
        validarPermisoEditar(idSolicitante);
        Usuario usuario = usuarioRepository.findById(idEditar)
                .orElseThrow(() -> new EntityNotFoundException("Usuario a editar no encontrado"));
        usuario.setNombre(usuarioRequestDTO.getNombre());
        usuario.setApellido(usuarioRequestDTO.getApellido());
        usuario.setRun(usuarioRequestDTO.getRun());
        usuario.setCorreo(usuarioRequestDTO.getCorreo());
        usuario.setFechaNacimiento(usuarioRequestDTO.getFechaNacimiento());
        usuario.setActivo(usuarioRequestDTO.isActivo());
        Rol rol = rolRepository.findById(usuarioRequestDTO.getRolId())
                .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado"));

        Set<Permiso> permisos = new HashSet<>();
        for (Long permisoId : usuarioRequestDTO.getPermiso()) {
            Permiso permiso = permisoRepository.findById(permisoId)
                    .orElseThrow(() -> new EntityNotFoundException("Permiso no encontrado"));
            permisos.add(permiso);
        }
        rol.setPermisos(permisos);

        usuario.setRol(rol);
        Usuario usuarioActualizado = usuarioRepository.save(usuario);
        return new UsuarioDTO(usuarioActualizado);
    }

    public UsuarioDTO obtenerUsuarioPorId(Long idUsuario) {
        return usuarioRepository.findUsuarioDTOById(idUsuario)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
    }



}
