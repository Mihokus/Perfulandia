package com.perfulandiaSpa.Perfulandia;
import com.perfulandiaSpa.Perfulandia.model.*;
import com.perfulandiaSpa.Perfulandia.repository.*;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.*;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    private PermisoRepository permisoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private RolRepository rolRepository;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        Random random = new Random();
        List<String> nombrePermisos = Arrays.asList(
                "CREAR_USUARIO",
                "EDITAR_USUARIO",
                "ELIMINAR_USUARIO",
                "CREAR_ROL",
                "CREAR_PERMISO"
        );
        List<Permiso> permisosGuardados = new ArrayList<>();
        for (String nombre : nombrePermisos) {
            Permiso permiso = new Permiso();
            permiso.setNombrePermiso(nombre);
            permisoRepository.save(permiso);
            permisosGuardados.add(permiso);
        }
        List<Rol> rolesGuardados = new ArrayList<>();

        // ADMIN
        Rol admin = new Rol();
        admin.setTipoRol("ADMIN");
        admin.setPermisos(new HashSet<>(permisosGuardados)); // todos los permisos
        rolRepository.save(admin);
        rolesGuardados.add(admin);


        Rol empleado = new Rol();
        empleado.setTipoRol("EMPLEADO");
        Set<Permiso> permisosEmpleado = new HashSet<>();
        permisosEmpleado.add(permisosGuardados.get(0));
        permisosEmpleado.add(permisosGuardados.get(1));
        empleado.setPermisos(permisosEmpleado);
        rolRepository.save(empleado);
        rolesGuardados.add(empleado);

        for (int i = 0; i < 10; i++) {
            Usuario usuario = new Usuario();
            usuario.setNombre(faker.name().firstName());
            usuario.setApellido(faker.name().lastName());
            usuario.setRun(faker.idNumber().valid());
            usuario.setCorreo(faker.internet().emailAddress());
            usuario.setFechaNacimiento(new Date());
            usuario.setActivo(true);

            Rol rolAleatorio = rolesGuardados.get(random.nextInt(rolesGuardados.size()));
            usuario.setRol(rolAleatorio);

            usuarioRepository.save(usuario);
        }



    }

}
