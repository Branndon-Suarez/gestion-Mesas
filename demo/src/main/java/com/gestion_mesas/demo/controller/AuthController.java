package com.gestion_mesas.demo.controller;

import com.gestion_mesas.demo.entity.Rol;
import com.gestion_mesas.demo.entity.Usuario;
import com.gestion_mesas.demo.repository.RolRepository;
import com.gestion_mesas.demo.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UsuarioRepository usuarioRepository, RolRepository rolRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/registrarse")
    public String mostrarRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "auth/registro";
    }

    @PostMapping("/registrarse")
    public String registrar(Usuario usuario, BindingResult result, Model model) {

        if (usuarioRepository.findByUsername(usuario.getUsername()).isPresent()) {
            result.rejectValue("username", "error.usuario", "El usuario ya existe");
        }

        if (result.hasErrors()) {
            return "auth/registro";
        }

        // 1. Cifrar contraseña
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        // 2. Rol por defecto → EMPLEADO
        Rol rolEmpleado = rolRepository.findByNombre("EMPLEADO").orElse(null);
        usuario.setRol(rolEmpleado);

        // 3. Guardar usuario
        usuarioRepository.save(usuario);

        return "redirect:/login?registroExitoso";
    }
}
