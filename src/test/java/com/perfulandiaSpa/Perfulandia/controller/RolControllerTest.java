package com.perfulandiaSpa.Perfulandia.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfulandiaSpa.Perfulandia.dto.request.RolRequestDTO;
import com.perfulandiaSpa.Perfulandia.dto.response.RolDTO;
import com.perfulandiaSpa.Perfulandia.model.Permiso;
import com.perfulandiaSpa.Perfulandia.model.Rol;
import com.perfulandiaSpa.Perfulandia.service.RolService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(RolController.class)
class RolControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RolService rolService;

    @Autowired
    private ObjectMapper mapper;
    private RolDTO rolDTO;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        rolDTO = new RolDTO();
        rolDTO.setId(1L);
        rolDTO.setTipoRol("ADMIN");
    }
    @Test
    public void testListaRoles() throws Exception {
        Permiso permiso = new Permiso();
        permiso.setId(1L);
        permiso.setNombrePermiso("CREAR_USUARIO");
        Rol rol = new Rol();
        rol.setId(1L);
        rol.setTipoRol("ADMIN");
        rol.setPermisos(Set.of(permiso));
        RolDTO rolDTO = new RolDTO(rol);
        when(rolService.listaRoles()).thenReturn(List.of(rolDTO));

        mockMvc.perform(get("/api/v1/roles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].tipoRol").value("ADMIN"))
                .andExpect(jsonPath("$[0].permisosOtorgados[0]").value("CREAR_USUARIO"));
    }

    @Test
    public void testCrearRol() throws Exception {
        Permiso permiso = new Permiso();
        permiso.setId(1L);
        permiso.setNombrePermiso("CREAR_USUARIO");
        Long idUsuario = 1L;
        RolRequestDTO rolRequestDTO = new RolRequestDTO();
        rolRequestDTO.setTipoRol("ADMIN");
        Rol rolGuardado = new Rol();
        rolGuardado.setId(1L);
        rolGuardado.setTipoRol("ADMIN");
        rolGuardado.setPermisos(Set.of(permiso));
        when(rolService.crearRol(any(RolRequestDTO.class), eq(idUsuario))).thenReturn(rolGuardado);
        mockMvc.perform(post("/api/v1/roles/{idUsuario}", idUsuario)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rolRequestDTO)))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.id").value(1))
                        .andExpect(jsonPath("$.tipoRol").value("ADMIN"))
                        .andExpect(jsonPath("$.permisosOtorgados[0]").value("CREAR_USUARIO"));
    }


}
