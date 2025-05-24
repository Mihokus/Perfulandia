package com.perfulandiaSpa.Perfulandia.service;

import com.perfulandiaSpa.Perfulandia.model.Usuario;
import com.perfulandiaSpa.Perfulandia.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;
import java.util.List;

@Service
@Transactional

public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Usuario findById(long id) {
        return usuarioRepository.findById(id).orElseThrow(( ) -> new EntityNotFoundException( "Usuario no encontrado" ));
    }

    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public void delete(long id) {
        usuarioRepository.deleteById(id);
    }


}
