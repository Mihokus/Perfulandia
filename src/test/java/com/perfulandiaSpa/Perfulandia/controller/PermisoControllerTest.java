package com.perfulandiaSpa.Perfulandia.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfulandiaSpa.Perfulandia.model.Permiso;
import com.perfulandiaSpa.Perfulandia.service.PermisoService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(PermisoController.class)
@SpringBootTest
public class PermisoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PermisoService permisoService;

    @Autowired
    private ObjectMapper mapper;

    private Permiso permiso;

    @BeforeEach
    void setUp() {

    }

    @Test
    void contextLoads() {

    }
}
