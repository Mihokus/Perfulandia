package com.perfulandiaSpa.Perfulandia.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfulandiaSpa.Perfulandia.dto.request.UsuarioRequestDTO;
import com.perfulandiaSpa.Perfulandia.dto.response.UsuarioDTO;
import com.perfulandiaSpa.Perfulandia.model.Usuario;
import com.perfulandiaSpa.Perfulandia.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import java.util.Date;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
class UsuarioControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Autowired
    private ObjectMapper objectMapper;
    private UsuarioDTO usuarioDTO;
    private UsuarioRequestDTO usuarioRequestDTO;

    @BeforeEach
    void setUp() {
        usuarioRequestDTO = new UsuarioRequestDTO();
        usuarioRequestDTO.setRun("20584564-9");
        usuarioRequestDTO.setNombre("Sebastian");
        usuarioRequestDTO.setApellido("Rodriguez");
        usuarioRequestDTO.setCorreo("Sebatian503@gmail.com");
        usuarioRequestDTO.setFechaNacimiento(new Date(100, 4, 23));
        usuarioRequestDTO.setActivo(true);
        usuarioRequestDTO.setRolId(1L);
        usuarioRequestDTO.setPermiso(Set.of(1L));

        usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(1L);
        usuarioDTO.setRun("20584564-9");
        usuarioDTO.setNombre("Sebastian");
        usuarioDTO.setApellido("Rodriguez");
        usuarioDTO.setCorreo("Sebatian503@gmail.com");
        usuarioDTO.setFechaNacimiento(new Date(100, 4, 23));
        usuarioDTO.setActivo(true);
        usuarioDTO.setRol("ADMIN");
        usuarioDTO.setPermiso(Set.of("CREAR_USUARIO"));
    }
    @Test
    public void testCrearUsuario() throws Exception {
        Long idUsuarioCreador = 1L;
        Usuario usuarioMock = new Usuario();
        usuarioMock.setId(1L);
        when(usuarioService.crearUsuario(eq(idUsuarioCreador), any(UsuarioRequestDTO.class)))
                .thenReturn(usuarioMock);

        mockMvc.perform(post("/api/v1/usuarios/{idUsuario}", idUsuarioCreador)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().string("usuario creado correctamente"));
    }
    @Test
    public void testObtenerUsuarioPorId() throws Exception {
        Long idUsuario = 1L;

        when(usuarioService.obtenerUsuarioPorId(idUsuario)).thenReturn(usuarioDTO);

        mockMvc.perform(get("/api/v1/usuarios/{idUsuario}", idUsuario))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(usuarioDTO.getId()))
                .andExpect(jsonPath("$.run").value(usuarioDTO.getRun()))
                .andExpect(jsonPath("$.nombre").value(usuarioDTO.getNombre()))
                .andExpect(jsonPath("$.apellido").value(usuarioDTO.getApellido()))
                .andExpect(jsonPath("$.correo").value(usuarioDTO.getCorreo()))
                .andExpect(jsonPath("$.activo").value(usuarioDTO.isActivo()))
                .andExpect(jsonPath("$.rol").value(usuarioDTO.getRol()))
                .andExpect(jsonPath("$.permiso[0]").value("CREAR_USUARIO"));
    }
    @Test
    public void testEliminarUsuario() throws Exception {
        Long idSolicitante = 1L;
        Long idEliminar = 2L;

        when(usuarioService.eliminarUsuario(idEliminar, idSolicitante))
                .thenReturn("Usuario eliminado correctamente");

        mockMvc.perform(delete("/api/v1/usuarios/{idSolicitante}/{id}", idSolicitante, idEliminar))
                .andExpect(status().isOk())
                .andExpect(content().string("Usuario eliminado correctamente"));
    }
    @Test
    public void testActualizarUsuario() throws Exception {
        Long idSolicitante = 1L;
        Long idEditar = 2L;
        UsuarioDTO usuarioEditado = new UsuarioDTO();
        usuarioEditado.setId(idEditar);
        usuarioEditado.setRun("20584564-9");
        usuarioEditado.setNombre("Sebastian");
        usuarioEditado.setApellido("Rodriguez");
        usuarioEditado.setCorreo("Sebatian503@gmail.com");
        usuarioEditado.setFechaNacimiento(new Date(100, 4, 23));
        usuarioEditado.setActivo(true);
        usuarioEditado.setRol("ADMIN");
        usuarioEditado.setPermiso(Set.of("CREAR_USUARIO"));
        when(usuarioService.editarUsuario(eq(idSolicitante), eq(idEditar), any(UsuarioRequestDTO.class)))
                .thenReturn(usuarioEditado);

        mockMvc.perform(put("/api/v1/usuarios/{idSolicitante}/{idEditar}", idSolicitante, idEditar)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(usuarioEditado.getId()))
                .andExpect(jsonPath("$.run").value(usuarioEditado.getRun()))
                .andExpect(jsonPath("$.nombre").value(usuarioEditado.getNombre()))
                .andExpect(jsonPath("$.apellido").value(usuarioEditado.getApellido()))
                .andExpect(jsonPath("$.correo").value(usuarioEditado.getCorreo()))
                .andExpect(jsonPath("$.activo").value(usuarioEditado.isActivo()))
                .andExpect(jsonPath("$.rol").value(usuarioEditado.getRol()))
                .andExpect(jsonPath("$.permiso[0]").value("CREAR_USUARIO"));
    }

}
