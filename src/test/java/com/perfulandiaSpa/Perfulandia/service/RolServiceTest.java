package com.perfulandiaSpa.Perfulandia.service;

import com.perfulandiaSpa.Perfulandia.dto.request.RolRequestDTO;
import com.perfulandiaSpa.Perfulandia.dto.response.RolDTO;
import com.perfulandiaSpa.Perfulandia.model.Permiso;
import com.perfulandiaSpa.Perfulandia.model.Rol;
import com.perfulandiaSpa.Perfulandia.model.Usuario;
import com.perfulandiaSpa.Perfulandia.repository.PermisoRepository;
import com.perfulandiaSpa.Perfulandia.repository.RolRepository;
import com.perfulandiaSpa.Perfulandia.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class RolServiceTest {

    @Autowired
    private RolService rolService;

    @MockBean
    private RolRepository rolRepository;

    @MockBean
    private PermisoRepository permisoRepository;

    @MockBean
    private UsuarioRepository usuarioRepository;

    private Usuario usuarioAdmin;

    @BeforeEach
    void setUp() {
        usuarioAdmin = crearUsuario(1L, true, "ADMIN", Set.of(crearPermiso(1L, "CREAR_ROL")));
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

    private Permiso crearPermiso(Long id, String nombrePermiso) {
        Permiso permiso = new Permiso();
        permiso.setId(id);
        permiso.setNombrePermiso(nombrePermiso);
        return permiso;
    }

    @Test
    void testValidacionDePermisosCrearRol() {
        when(usuarioRepository.count()).thenReturn(1L);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioAdmin));

        assertDoesNotThrow(() -> rolService.validacionDePermisosCrearRol(1L));
    }

    @Test
    void testCrearRol() {
        when(usuarioRepository.count()).thenReturn(1L);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioAdmin));

        when(permisoRepository.findById(1L)).thenReturn(Optional.of(crearPermiso(1L, "CREAR_ROL")));

        Rol rolGuardado = new Rol();
        rolGuardado.setId(1L);
        rolGuardado.setTipoRol("ADMIN");
        rolGuardado.setPermisos(Set.of(crearPermiso(1L, "CREAR_ROL")));

        when(rolRepository.save(any(Rol.class))).thenReturn(rolGuardado);

        RolRequestDTO rolRequestDTO = new RolRequestDTO();
        rolRequestDTO.setTipoRol("ADMIN");
        rolRequestDTO.setPermisoId(Set.of(1L));

        Rol rolCreado = rolService.crearRol(rolRequestDTO, 1L);

        assertNotNull(rolCreado);
        assertEquals("ADMIN", rolCreado.getTipoRol());
        assertEquals(1, rolCreado.getPermisos().size());
    }

    @Test
    void testListaRoles() {
        RolDTO rolDTO = new RolDTO();
        rolDTO.setId(1L);
        rolDTO.setTipoRol("ADMIN");

        when(rolRepository.buscarRoles()).thenReturn(List.of(rolDTO));

        List<RolDTO> lista = rolService.listaRoles();

        assertNotNull(lista);
        assertEquals(1, lista.size());
        assertEquals("ADMIN", lista.get(0).getTipoRol());
    }

    @Test
    void testValidacionUsuarioInactivo() {
        Usuario usuarioInactivo = crearUsuario(2L, false, "EMPLEADO", Set.of());

        when(usuarioRepository.count()).thenReturn(1L);
        when(usuarioRepository.findById(2L)).thenReturn(Optional.of(usuarioInactivo));

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
                () -> rolService.validacionDePermisosCrearRol(2L));
        assertEquals("El usuario está desactivado y no puede realizar acciones", ex.getMessage());
    }

    @Test
    void testUserSinPermisos() {
        Set<Permiso> permisosSinCrearRol = Set.of(crearPermiso(2L, "OTRO_PERMISO"));
        Usuario usuarioSinPermiso = crearUsuario(3L, true, "EMPLEADO", permisosSinCrearRol);

        when(usuarioRepository.count()).thenReturn(1L);
        when(usuarioRepository.findById(3L)).thenReturn(Optional.of(usuarioSinPermiso));

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
                () -> rolService.validacionDePermisosCrearRol(3L));
        assertEquals("El usuario no tiene los permisos", ex.getMessage());
    }
}