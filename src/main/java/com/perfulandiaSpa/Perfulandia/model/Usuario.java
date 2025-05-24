package com.perfulandiaSpa.Perfulandia.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "usuario")
@AllArgsConstructor
@NoArgsConstructor
@Data



public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, length = 13, nullable = false)
    private String run;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column (nullable = false)
    private String correo;

    @Column(nullable = false)
    private Date fechaNacimiento;

    @Column(nullable = false)
    private boolean activo = true;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rol_Id")
    private Rol rol;


}
