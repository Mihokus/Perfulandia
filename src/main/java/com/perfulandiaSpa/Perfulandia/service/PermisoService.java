package com.perfulandiaSpa.Perfulandia.service;
import com.perfulandiaSpa.Perfulandia.dto.request.PermisoRequestDTO;
import com.perfulandiaSpa.Perfulandia.dto.response.PermisoDTO;
import com.perfulandiaSpa.Perfulandia.model.Permiso;
import com.perfulandiaSpa.Perfulandia.model.Usuario;
import com.perfulandiaSpa.Perfulandia.repository.PermisoRepository;
import com.perfulandiaSpa.Perfulandia.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermisoService {
    @Autowired
    private PermisoRepository permisoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public void validacionDePermisosCrearPermiso(Long idUsuario) {
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
            if("CREAR_PERMISO".equalsIgnoreCase(permiso.getNombrePermiso())){
                return;
            }
        }
        throw new EntityNotFoundException("El usuario no tiene los permisos");
    }

    public PermisoDTO crearPermiso (PermisoRequestDTO permisoRequestDTO, Long idUsuario) {
        validacionDePermisosCrearPermiso(idUsuario);
        Permiso permiso = new Permiso();
        permiso.setNombrePermiso(permisoRequestDTO.getNombrePermiso());
        Permiso permisoGuardado = permisoRepository.save(permiso);
        return new PermisoDTO(permisoGuardado);
    }

    public List<PermisoDTO> listaPermiso() {
        return permisoRepository.listaPermisos();
    }
}
