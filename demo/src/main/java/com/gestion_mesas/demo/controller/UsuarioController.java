package com.gestion_mesas.demo.controller;

import com.gestion_mesas.demo.entity.Usuario;
import com.gestion_mesas.demo.repository.RolRepository;
import com.gestion_mesas.demo.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {
    private final UsuarioRepository usuarioRepo;
    private final RolRepository rolRepo;
    private final PasswordEncoder encoder;

    public UsuarioController(UsuarioRepository usuarioRepo, RolRepository rolRepo, PasswordEncoder encoder) {
        this.usuarioRepo = usuarioRepo;
        this.rolRepo = rolRepo;
        this.encoder = encoder;
    }

    @GetMapping("/listar")
    public String listar(Model model) {
        model.addAttribute("usuarios", usuarioRepo.findAll());
        return "usuarios/listar";
    }

    @GetMapping("/crear")
    public String crear(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("roles", rolRepo.findAll());
        return "usuarios/crear";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Usuario usuario) {

        usuario.setPassword(encoder.encode(usuario.getPassword()));
        usuarioRepo.save(usuario);
        return "redirect:/usuarios/listar";
    }
}
