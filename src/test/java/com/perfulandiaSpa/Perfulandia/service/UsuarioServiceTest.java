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
import org.junit.jupiter.api.BeforeEach;
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
class UsuarioServiceTest {

    @Autowired
    private UsuarioService usuarioService;

    @MockBean
    private UsuarioRepository usuarioRepository;

    @MockBean
    private RolRepository rolRepository;

    @MockBean
    private PermisoRepository permisoRepository;

    private Usuario usuarioAdmin;

    private Rol rolAdmin;

    @BeforeEach
    void setUp() {
        Permiso permisoCrearUsuario = crearPermiso(1L, "CREAR_USUARIO");
        Set<Permiso> permisosAdmin = Set.of(permisoCrearUsuario);

        rolAdmin = new Rol();
        rolAdmin.setId(1L);
        rolAdmin.setTipoRol("ADMIN");
        rolAdmin.setPermisos(permisosAdmin);

        usuarioAdmin = new Usuario();
        usuarioAdmin.setId(1L);
        usuarioAdmin.setActivo(true);
        usuarioAdmin.setRol(rolAdmin);
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

    private UsuarioRequestDTO crearUsuarioRequestDTO() {
        UsuarioRequestDTO dto = new UsuarioRequestDTO();
        dto.setNombre("Sebastian");
        dto.setApellido("Rodriguez");
        dto.setRun("20584564-9");
        dto.setCorreo("sebastian@example.com");
        dto.setFechaNacimiento(new Date(100, 4, 23)); // 2020-05-23
        dto.setActivo(true);
        dto.setRolId(1L);
        dto.setPermiso(Set.of(1L));
        return dto;
    }

    @Test
    void testValidacionDePermisosAdmin() {
        when(usuarioRepository.count()).thenReturn(1L);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioAdmin));

        assertDoesNotThrow(() -> usuarioService.validacionDePermisos(1L));
    }

    @Test
    void testCrearUsuario() {
        UsuarioRequestDTO dto = crearUsuarioRequestDTO();

        when(usuarioRepository.count()).thenReturn(1L);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioAdmin));
        when(rolRepository.findById(dto.getRolId())).thenReturn(Optional.of(rolAdmin));
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Usuario usuarioCreado = usuarioService.crearUsuario(1L, dto);

        assertNotNull(usuarioCreado);
        assertEquals(dto.getNombre(), usuarioCreado.getNombre());
        assertEquals(dto.getRolId(), usuarioCreado.getRol().getId());
    }

    @Test
    void testListarUsuarios() {
        Usuario usuario = new Usuario();
        usuario.setId(2L);
        usuario.setNombre("Maria");
        usuario.setApellido("Lopez");
        usuario.setRun("12345678-9");
        usuario.setCorreo("maria@example.com");
        usuario.setFechaNacimiento(new Date(100, 4, 23));
        usuario.setActivo(true);
        usuario.setRol(rolAdmin);

        when(usuarioRepository.findAll()).thenReturn(List.of(usuario));

        List<UsuarioDTO> lista = usuarioService.listarUsuarios();

        assertNotNull(lista);
        assertEquals(1, lista.size());
        assertEquals("Maria", lista.get(0).getNombre());
        assertEquals("ADMIN", lista.get(0).getRol());
        assertTrue(lista.get(0).getPermiso().contains("CREAR_USUARIO"));
    }

    @Test
    void testEliminarUsuario() {
        Usuario solicitante = usuarioAdmin;
        when(usuarioRepository.findById(solicitante.getId())).thenReturn(Optional.of(solicitante));
        doNothing().when(usuarioRepository).deleteById(2L);
        when(usuarioRepository.count()).thenReturn(1L);

        String resultado = usuarioService.eliminarUsuario(2L, solicitante.getId());

        assertEquals("Usuario eliminado", resultado);
        verify(usuarioRepository, times(1)).deleteById(2L);
    }

    @Test
    void testEditarUsuario() {
        Usuario usuarioExistente = crearUsuario(2L, true, "EMPLEADO", Set.of(crearPermiso(2L, "EDITAR_USUARIO")));
        when(usuarioRepository.findById(2L)).thenReturn(Optional.of(usuarioExistente));
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioAdmin));
        when(rolRepository.findById(1L)).thenReturn(Optional.of(rolAdmin));
        when(permisoRepository.findById(1L)).thenReturn(Optional.of(crearPermiso(1L, "CREAR_USUARIO")));
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UsuarioRequestDTO dto = crearUsuarioRequestDTO();
        UsuarioDTO usuarioDTO = usuarioService.editarUsuario(1L, 2L, dto);

        assertNotNull(usuarioDTO);
        assertEquals(dto.getNombre(), usuarioDTO.getNombre());
    }

    @Test
    void testObtenerUsuarioPorId() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(1L);
        usuarioDTO.setNombre("Sebastian");
        usuarioDTO.setApellido("Rodriguez");
        usuarioDTO.setRun("20584564-9");
        usuarioDTO.setCorreo("sebastian@example.com");
        usuarioDTO.setFechaNacimiento(new Date(100,4,23));
        usuarioDTO.setActivo(true);
        usuarioDTO.setRol("ADMIN");
        usuarioDTO.setPermiso(Set.of("CREAR_USUARIO"));

        when(usuarioRepository.findUsuarioDTOById(1L)).thenReturn(Optional.of(usuarioDTO));

        UsuarioDTO resultado = usuarioService.obtenerUsuarioPorId(1L);

        assertNotNull(resultado);
        assertEquals("Sebastian", resultado.getNombre());
    }

    @Test
    void testValidacionDeUsuarioInactivo() {
        Usuario usuarioInactivo = crearUsuario(3L, false, "EMPLEADO", Set.of());

        when(usuarioRepository.count()).thenReturn(1L);
        when(usuarioRepository.findById(3L)).thenReturn(Optional.of(usuarioInactivo));

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
                () -> usuarioService.validacionDePermisos(3L));
        assertEquals("El usuario está desactivado y no puede realizar acciones", ex.getMessage());
    }

    @Test
    void testValidacionDePermisosr() {
        Set<Permiso> permisosSinCrearUsuario = Set.of(crearPermiso(2L, "OTRO_PERMISO"));
        Usuario usuarioSinPermiso = crearUsuario(4L, true, "EMPLEADO", permisosSinCrearUsuario);

        when(usuarioRepository.count()).thenReturn(1L);
        when(usuarioRepository.findById(4L)).thenReturn(Optional.of(usuarioSinPermiso));

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
                () -> usuarioService.validacionDePermisos(4L));
        assertEquals("El usuario no tiene los permisos", ex.getMessage());
    }

}