package com.gestion_mesas.demo.controller;

import com.gestion_mesas.demo.entity.DetallePago;
import com.gestion_mesas.demo.entity.Pedido;
import com.gestion_mesas.demo.service.impl.ClienteService;
import com.gestion_mesas.demo.service.impl.DetallePagoService;
import com.gestion_mesas.demo.service.impl.PedidoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/pagos")
public class PagoController {
    private static final String REDIRECT_LISTAR_MESAS = "redirect:/mesas/listar";
    private static final String REDIRECT_LISTAR_PAGOS = "redirect:/pagos/listar/";

    private final DetallePagoService detallePagoService;
    private final ClienteService clienteService;
    private final PedidoService pedidoService;

    public PagoController(DetallePagoService detallePagoService,
                          ClienteService clienteService,
                          PedidoService pedidoService) {
        this.detallePagoService = detallePagoService;
        this.clienteService = clienteService;
        this.pedidoService = pedidoService;
    }

    @GetMapping("/listarPagosGenerales")
    public String listarPagos(Model model) {
        List<DetallePago> pagos = detallePagoService.listarPagos();
        model.addAttribute("listaPagosGenerales", pagos);
        return "pagos/listar-pagos-generales";
    }

    @GetMapping("/nuevo/{idPedido}")
    public String nuevoPago(@PathVariable Long idPedido, Model model) {
        Pedido pedido = pedidoService.buscarPorId(idPedido);
        if (pedido == null) return REDIRECT_LISTAR_MESAS;

        DetallePago dp = new DetallePago();
        dp.setPedido(pedido);

        model.addAttribute("pedido", pedido);
        model.addAttribute("pago", dp);
        model.addAttribute("clientes", clienteService.listar());
        model.addAttribute("metodos", DetallePago.MetodoPago.values());
        model.addAttribute("pagosExistentes", detallePagoService.listarPorPedido(idPedido));
        model.addAttribute("totalPagado", detallePagoService.totalPagadoPorPedido(idPedido));

        return "pagos/nuevo";
    }

    @PostMapping("/guardar")
    public String guardarPago(@ModelAttribute DetallePago pago) {
        // pago.pedido debe venir con id (th:field en el form)
        detallePagoService.guardarPago(pago);
        Long idPedido = pago.getPedido().getIdPedido();
        return REDIRECT_LISTAR_PAGOS + idPedido;
    }

    @GetMapping("/listar/{idPedido}")
    public String listarPagos(@PathVariable Long idPedido, Model model) {
        Pedido pedido = pedidoService.buscarPorId(idPedido);
        if (pedido == null) return REDIRECT_LISTAR_MESAS;

        Double totalPagado = detallePagoService.totalPagadoPorPedido(idPedido);
        Double faltante = pedido.getTotal() - totalPagado;

        model.addAttribute("pedido", pedido);
        model.addAttribute("listaPagos", detallePagoService.listarPorPedido(idPedido));
        model.addAttribute("totalPagado", totalPagado);
        model.addAttribute("faltante", faltante);

        return "pagos/listar";
    }

    @PostMapping("/eliminar/{idPago}")
    public String eliminarPago(@PathVariable Long idPago) {
        DetallePago dp = detallePagoService.buscarPorId(idPago);
        Long idPedido = dp != null && dp.getPedido() != null ? dp.getPedido().getIdPedido() : null;
        detallePagoService.eliminarPago(idPago);
        if (idPedido != null) return REDIRECT_LISTAR_PAGOS + idPedido;
        return REDIRECT_LISTAR_MESAS;
    }
}
