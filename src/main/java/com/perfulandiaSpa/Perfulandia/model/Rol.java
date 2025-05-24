package com.perfulandiaSpa.Perfulandia.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;

@Entity
@Table(name = "rol")
@Data
@AllArgsConstructor
@NoArgsConstructor


public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String tipoRol;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Permisos> permisos;


}
