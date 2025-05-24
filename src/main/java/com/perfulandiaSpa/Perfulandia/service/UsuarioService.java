package com.perfulandiaSpa.Perfulandia.service;

import com.perfulandiaSpa.Perfulandia.dto.request.UsuarioRequestDTO;
import com.perfulandiaSpa.Perfulandia.model.Rol;
import com.perfulandiaSpa.Perfulandia.model.Usuario;
import com.perfulandiaSpa.Perfulandia.repository.RolRepository;
import com.perfulandiaSpa.Perfulandia.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;
import java.util.List;
import java.util.Optional;

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

        Optional<Rol> rol = rolRepository.findById(usuarioRequestDTO.getRoleId());
        if (rol.isEmpty()){
            throw new EntityNotFoundException("Rol no encontrado");
        }
        usuario.setRol(rol.get());
        return usuarioRepository.save(usuario);
    }
}
