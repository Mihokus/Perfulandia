package com.perfulandiaSpa.Perfulandia.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Set;

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
    private Set<Permiso> permisos;

}
