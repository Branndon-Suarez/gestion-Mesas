package com.gestion_mesas.demo.controller;

import com.gestion_mesas.demo.entity.Producto;
import com.gestion_mesas.demo.service.impl.ProductoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/productos")
public class ProductoController {
    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    // Listar productos
    @GetMapping("/listar")
    public String listarProductos(Model model) {
        model.addAttribute("listaProductos", productoService.listarProductos());
        return "producto/listar";
    }

    // Mostrar formulario de crear producto
    @GetMapping("/formGuardar")
    public String mostrarFormGuardar(Model model) {
        model.addAttribute("producto", new Producto());
        return "producto/formGuardar";
    }

    // Guardar producto
    @PostMapping("/guardar")
    public String guardarProducto(@Valid @ModelAttribute("producto") Producto producto,
                                  BindingResult result) {
        if (result.hasErrors()) {
            return "producto/formGuardar";
        }
        productoService.guardarProducto(producto);
        return "redirect:/productos/listar";
    }

    // Mostrar formulario de editar producto
    @GetMapping("/formAct/{id}")
    public String mostrarFormActualizar(@PathVariable Long id, Model model) {
        Producto producto = productoService.buscarPorId(id);
        if (producto == null) {
            return "redirect:/productos/listar";
        }
        model.addAttribute("producto", producto);
        return "producto/formAct";
    }

    // Actualizar producto
    @PostMapping("/actualizar")
    public String actualizarProducto(@Valid @ModelAttribute("producto") Producto producto,
                                     BindingResult result) {
        if (result.hasErrors()) {
            return "producto/formAct";
        }
        productoService.guardarProducto(producto);
        return "redirect:/productos/listar";
    }

    // Eliminar producto
    @PostMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return "redirect:/productos/listar";
    }
}
