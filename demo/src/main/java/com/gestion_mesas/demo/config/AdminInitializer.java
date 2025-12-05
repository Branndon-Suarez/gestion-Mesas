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
    @Bean
    CommandLineRunner initRolesAndAdmin(RolRepository rolRepo, UsuarioRepository userRepo, PasswordEncoder encoder) {
        return args -> {

            // Crear rol ADMIN si no existe
            if (rolRepo.findByNombre("ADMIN").isEmpty()) {
                rolRepo.save(new Rol(null, "ADMIN"));
            }

            if (rolRepo.findByNombre("EMPLEADO").isEmpty()) {
                rolRepo.save(new Rol(null, "EMPLEADO"));
            }

            // Crear usuario ADMIN si no existe
            if (userRepo.findByUsername("admin").isEmpty()) {
                Usuario admin = new Usuario();
                admin.setUsername("admin");
                admin.setPassword(encoder.encode("admin123"));
                admin.setRol(rolRepo.findByNombre("ADMIN").get());
                userRepo.save(admin);
                System.out.println("Usuario ADMIN creado: admin / admin123");
            }
        };
    }
}
