package com.gestion_mesas.demo.config;

import com.gestion_mesas.demo.entity.Rol;
import com.gestion_mesas.demo.entity.Usuario;
import com.gestion_mesas.demo.repository.RolRepository;
import com.gestion_mesas.demo.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminInitializer {
    private static final String ROL_ADMIN = "ADMIN";
    private static final String ROL_EMPLEADO = "EMPLEADO";

    @Bean
    CommandLineRunner initRolesAndAdmin(RolRepository rolRepo, UsuarioRepository userRepo, PasswordEncoder encoder) {
        return args -> {

            // Crear rol ADMIN si no existe
            if (rolRepo.findByNombre(ROL_ADMIN).isEmpty()) {
                rolRepo.save(new Rol(null, ROL_ADMIN));
            }

            if (rolRepo.findByNombre(ROL_EMPLEADO).isEmpty()) {
                rolRepo.save(new Rol(null, ROL_EMPLEADO));
            }

            // Crear usuario ADMIN si no existe
            if (userRepo.findByUsername("admin").isEmpty()) {
                Usuario admin = new Usuario();
                admin.setUsername("admin");
                admin.setPassword(encoder.encode("admin123"));
                admin.setRol(rolRepo.findByNombre(ROL_ADMIN).get());
                userRepo.save(admin);
                System.out.println("Usuario ADMIN creado: admin / admin123");
            }
        };
    }
}
