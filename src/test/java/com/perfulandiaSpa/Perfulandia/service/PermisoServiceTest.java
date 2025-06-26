package com.perfulandiaSpa.Perfulandia.service;

import com.perfulandiaSpa.Perfulandia.dto.request.PermisoRequestDTO;
import com.perfulandiaSpa.Perfulandia.dto.response.PermisoDTO;
import com.perfulandiaSpa.Perfulandia.model.Permiso;
import com.perfulandiaSpa.Perfulandia.model.Rol;
import com.perfulandiaSpa.Perfulandia.model.Usuario;
import com.perfulandiaSpa.Perfulandia.repository.PermisoRepository;
import com.perfulandiaSpa.Perfulandia.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class PermisoServiceTest {

    @Autowired
    private PermisoService permisoService;

    @MockBean
    private PermisoRepository permisoRepository;

    @MockBean
    private UsuarioRepository usuarioRepository;

    @Test
    public void testValidacionDeRol() {
        Usuario admin = crearUsuario(1L, true, "ADMIN", Set.of());
        when(usuarioRepository.count()).thenReturn(1L);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(admin));

        assertDoesNotThrow(() -> permisoService.validacionDePermisosCrearPermiso(1L));
    }

    @Test
    public void testValidacionPermisoCrearPermiso() {
        Permiso permisoCrear = new Permiso();
        permisoCrear.setNombrePermiso("CREAR_PERMISO");

        Usuario user = crearUsuario(2L, true, "EMPLEADO", Set.of(permisoCrear));
        when(usuarioRepository.count()).thenReturn(1L);
        when(usuarioRepository.findById(2L)).thenReturn(Optional.of(user));

        assertDoesNotThrow(() -> permisoService.validacionDePermisosCrearPermiso(2L));
    }

    @Test
    public void testValidacionDePermisos() {
        Usuario user = crearUsuario(3L, true, "EMPLEADO", Set.of());
        when(usuarioRepository.count()).thenReturn(1L);
        when(usuarioRepository.findById(3L)).thenReturn(Optional.of(user));

        assertThrows(EntityNotFoundException.class, () -> permisoService.validacionDePermisosCrearPermiso(3L));
    }

    @Test
    public void testValidacionUsuarioInactivo() {
        Usuario user = crearUsuario(4L, false, "ADMIN", Set.of());
        when(usuarioRepository.count()).thenReturn(1L);
        when(usuarioRepository.findById(4L)).thenReturn(Optional.of(user));

        assertThrows(EntityNotFoundException.class, () -> permisoService.validacionDePermisosCrearPermiso(4L));
    }

    @Test
    public void testCrearPermiso() {
        Usuario admin = crearUsuario(1L, true, "ADMIN", Set.of());
        PermisoRequestDTO dto = new PermisoRequestDTO();
        dto.setNombrePermiso("CREAR_USUARIO");

        Permiso permiso = new Permiso();
        permiso.setId(1L);
        permiso.setNombrePermiso("CREAR_USUARIO");

        when(usuarioRepository.count()).thenReturn(1L);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(admin));
        when(permisoRepository.save(any(Permiso.class))).thenReturn(permiso);

        PermisoDTO result = permisoService.crearPermiso(dto, 1L);
        assertNotNull(result);
        assertEquals("CREAR_USUARIO", result.getNombrePermiso());
    }

    @Test
    public void testListaPermiso() {
        PermisoDTO permisoDTO = new PermisoDTO();
        permisoDTO.setId(1L);
        permisoDTO.setNombrePermiso("CREAR_USUARIO");

        when(permisoRepository.listaPermisos()).thenReturn(List.of(permisoDTO));

        List<PermisoDTO> permisos = permisoService.listaPermiso();

        assertEquals(1, permisos.size());
        assertEquals("CREAR_USUARIO", permisos.get(0).getNombrePermiso());
    }

    private Usuario crearUsuario(Long id, boolean activo, String tipoRol, Set<Permiso> permisos) {
        Rol rol = new Rol();
        rol.setTipoRol(tipoRol);
        rol.setPermisos(permisos);
        Usuario user = new Usuario();
        user.setId(id);
        user.setActivo(activo);
        user.setRol(rol);

        return user;
    }
}