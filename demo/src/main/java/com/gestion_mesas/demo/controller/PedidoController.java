package com.gestion_mesas.demo.controller;

import com.gestion_mesas.demo.entity.Mesa;
import com.gestion_mesas.demo.entity.Pedido;
import com.gestion_mesas.demo.service.impl.MesaService;
import com.gestion_mesas.demo.service.impl.PedidoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {
    public final PedidoService pedidoService;
    public final MesaService mesaService;
    public PedidoController(PedidoService pedidoService, MesaService mesaService) {
        this.pedidoService = pedidoService;
        this.mesaService = mesaService;
    }

    @GetMapping("/listar")
    public String listarPedidos(Model model) {
        model.addAttribute("listaPedidos", pedidoService.listarPedidos());
        return "pedido/listar";
    }

    @GetMapping("/guardar/{idMesa}")
    public String guardarPedido(@PathVariable Long idMesa) {
        Mesa mesa = mesaService.buscarById(idMesa);

        pedidoService.guardarPedido(mesa);
        return "redirect:/mesas/listar";
    }

    @PostMapping("/cancelar/{id}")
    public String cancelarPedido(@PathVariable Long id) {
        Pedido pedido = pedidoService.buscarPorId(id);
        if (pedido != null) {

            pedido.setEstadoPago(Pedido.estadoPago.CANCELADO);

            Mesa mesa = pedido.getMesa();
            mesa.setEstado(Mesa.estadoMesa.LIBRE);

            pedidoService.guardarPedido(pedido);
        }

        return "redirect:/mesas/listar";
    }
}
