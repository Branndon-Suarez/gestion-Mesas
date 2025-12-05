package com.gestion_mesas.demo.controller;

import com.gestion_mesas.demo.entity.Pedido;
import com.gestion_mesas.demo.entity.Producto;
import com.gestion_mesas.demo.entity.DetallePedido;
import com.gestion_mesas.demo.service.impl.DetallePedidoService;
import com.gestion_mesas.demo.service.impl.PedidoService;
import com.gestion_mesas.demo.service.impl.ProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/detalle-pedido")
public class DetallePedidoController {
    private final DetallePedidoService detalleService;
    private final PedidoService pedidoService;
    private final ProductoService productoService;

    public DetallePedidoController(DetallePedidoService detalleService,
                                   PedidoService pedidoService,
                                   ProductoService productoService) {
        this.detalleService = detalleService;
        this.pedidoService = pedidoService;
        this.productoService = productoService;
    }

    @GetMapping("/listar/{idPedido}")
    public String listarDetalles(@PathVariable Long idPedido, Model model) {
        Pedido pedido = pedidoService.buscarPorId(idPedido); // O busca por id
        List<DetallePedido> detalles = detalleService.listarPorPedido(idPedido);
        List<Producto> productos = productoService.listarProductos();

        model.addAttribute("pedido", pedido);
        model.addAttribute("detalles", detalles);
        model.addAttribute("productos", productos);

        return "detallePedido/listar";
    }

    @PostMapping("/agregar")
    public String agregarDetalle(@RequestParam Long idPedido,
                                 @RequestParam Long idProducto,
                                 @RequestParam Integer cantidad) {

        Producto producto = productoService.buscarPorId(idProducto);
        Pedido pedido = pedidoService.buscarPorId(idPedido);

        DetallePedido detalle = new DetallePedido();
        detalle.setPedido(pedido);
        detalle.setProducto(producto);
        detalle.setCantidad(cantidad);
        detalle.setPrecioUnitario(producto.getPrecio());

        detalleService.guardarDetalle(detalle);

        // Actualizar total del pedido
        double total = detalleService.listarPorPedido(idPedido)
                .stream()
                .mapToDouble(DetallePedido::getSubtotal)
                .sum();
        pedido.setTotal(total);
        pedidoService.guardarPedido(pedido);

        return "redirect:/detalle-pedido/listar/" + idPedido;
    }

    @PostMapping("/eliminar/{idDetalle}")
    public String eliminarDetalle(@PathVariable Long idDetalle) {
        DetallePedido detalle = detalleService.buscarPorId(idDetalle);
        if (detalle != null) {
            Long idPedido = detalle.getPedido().getIdPedido();
            detalleService.eliminarDetalle(idDetalle);

            // Recalcular total
            double total = detalleService.listarPorPedido(idPedido)
                    .stream()
                    .mapToDouble(DetallePedido::getSubtotal)
                    .sum();
            Pedido pedido = pedidoService.buscarPorId(idPedido);
            pedido.setTotal(total);
            pedidoService.guardarPedido(pedido);

            return "redirect:/detalle-pedido/listar/" + idPedido;
        }
        return "redirect:/mesas/listar";
    }
}
