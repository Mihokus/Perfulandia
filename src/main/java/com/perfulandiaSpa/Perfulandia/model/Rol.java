package com.perfulandiaSpa.Perfulandia.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.HashSet;
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
    @JoinTable(
            name = "rolPermiso",
            joinColumns = @JoinColumn(name = "rol_Id"),
            inverseJoinColumns = @JoinColumn(name = "permiso_Id"))
    private Set<Permiso> permisos = new HashSet<>();

}
