package com.gestion_mesas.demo.controller;

import com.gestion_mesas.demo.entity.Cliente;
import com.gestion_mesas.demo.service.impl.ClienteService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/clientes")
public class ClienteController {
    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/listar")
    public String listar(Model model) {
        model.addAttribute("listaClientes", clienteService.listar());
        return "cliente/listar";
    }

    @GetMapping("/formGuardar")
    public String formGuardar(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "cliente/formGuardar";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("cliente") Cliente cliente, BindingResult result) {
        if (result.hasErrors()) return "cliente/formGuardar";
        clienteService.guardar(cliente);
        return "redirect:/clientes/listar";
    }

    @GetMapping("/formAct/{id}")
    public String formAct(@PathVariable Long id, Model model) {
        Cliente c = clienteService.buscarPorId(id);
        if (c == null) return "redirect:/clientes/listar";
        model.addAttribute("cliente", c);
        return "cliente/formAct";
    }

    @PostMapping("/actualizar")
    public String actualizar(@Valid @ModelAttribute("cliente") Cliente cliente, BindingResult result) {
        if (result.hasErrors()) return "cliente/formAct";
        clienteService.guardar(cliente);
        return "redirect:/clientes/listar";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        clienteService.eliminar(id);
        return "redirect:/clientes/listar";
    }
}
