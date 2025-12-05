package com.gestion_mesas.demo.controller;

import com.gestion_mesas.demo.entity.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

//    @GetMapping("/registrarse")
//    public String registrar(Model model) {
//        model.addAttribute("usuario", new Usuario());
//        return "auth/registro";
//    }
}
