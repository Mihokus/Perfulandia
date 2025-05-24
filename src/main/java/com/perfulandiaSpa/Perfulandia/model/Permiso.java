package com.perfulandiaSpa.Perfulandia.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "permisos")
@AllArgsConstructor
@NoArgsConstructor
@Data


public class Permiso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String nombrePermiso;


}
