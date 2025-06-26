package com.perfulandiaSpa.Perfulandia.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfulandiaSpa.Perfulandia.dto.request.PermisoRequestDTO;
import com.perfulandiaSpa.Perfulandia.dto.response.PermisoDTO;
import com.perfulandiaSpa.Perfulandia.model.Permiso;
import com.perfulandiaSpa.Perfulandia.service.PermisoService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PermisoController.class)
public class PermisoControllerTest {
        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private PermisoService permisoService;

        @Autowired
        private ObjectMapper mapper;
        private PermisoDTO permisoDTO;
        @Autowired
        private ObjectMapper objectMapper;

        @BeforeEach
        void setUp() {
            permisoDTO = new PermisoDTO();
            permisoDTO.setId(1L);
            permisoDTO.setNombrePermiso("CREAR_USUARIO");
        }
        @Test
        public void testListarPermisos() throws Exception {
            when(permisoService.listaPermiso()).thenReturn(List.of(permisoDTO));
            mockMvc.perform(get("/api/v1/permisos"))
                    .andExpect(status().isOk()) //
                    .andExpect(jsonPath("$[0].id").value(1))
                    .andExpect(jsonPath("$[0].nombrePermiso").value("CREAR_USUARIO"));
        }

    @Test
    public void testCrearPermiso() throws Exception {
        Long idUsuario = 1L;

        PermisoRequestDTO permisoRequestDTO = new PermisoRequestDTO();
        permisoRequestDTO.setNombrePermiso("CREAR_USUARIO");

        PermisoDTO permisoDTO = new PermisoDTO();
        permisoDTO.setId(1L);
        permisoDTO.setNombrePermiso("CREAR_USUARIO");
        when(permisoService.crearPermiso(any(), eq(idUsuario))).thenReturn(permisoDTO);

        mockMvc.perform(post("/api/v1/permisos/{idUsuario}", idUsuario)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(permisoRequestDTO)))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.id").value(1))
                        .andExpect(jsonPath("$.nombrePermiso").value("CREAR_USUARIO"));
    }
    }


