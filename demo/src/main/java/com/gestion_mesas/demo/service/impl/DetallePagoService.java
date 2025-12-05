package com.gestion_mesas.demo.service.impl;

import com.gestion_mesas.demo.entity.DetallePago;
import com.gestion_mesas.demo.entity.Mesa;
import com.gestion_mesas.demo.entity.Pedido;
import com.gestion_mesas.demo.repository.DetallePagoRepository;
import com.gestion_mesas.demo.repository.MesaRepository;
import com.gestion_mesas.demo.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DetallePagoService {
    private final DetallePagoRepository pagoRepo;
    private final PedidoRepository pedidoRepo;
    private final MesaRepository mesaRepo;

    public DetallePagoService(DetallePagoRepository pagoRepo,
                              PedidoRepository pedidoRepo,
                              MesaRepository mesaRepo) {
        this.pagoRepo = pagoRepo;
        this.pedidoRepo = pedidoRepo;
        this.mesaRepo = mesaRepo;
    }

    public List<DetallePago> listarPagos() {
        return pagoRepo.findAll();
    }

    public DetallePago guardarPago(DetallePago pago) {
        pago.setFechaPago(LocalDateTime.now());
        DetallePago guardado = pagoRepo.save(pago);

        // Recalcular total pagado
        Long idPedido = pago.getPedido().getIdPedido();
        Double totalPagado = pagoRepo.totalPagadoPorPedido(idPedido);
        Pedido pedido = pedidoRepo.findById(idPedido).orElse(null);
        if (pedido != null) {
            if (totalPagado >= pedido.getTotal()) {
                pedido.setEstadoPago(Pedido.estadoPago.Pagado);
                pedido.setFechaHoraCierre(LocalDateTime.now());

                // Liberar mesa
                Mesa mesa = pedido.getMesa();
                if (mesa != null) {
                    mesa.setEstado(Mesa.estadoMesa.Libre);
                    mesaRepo.save(mesa);
                }
            } else {
                pedido.setEstadoPago(Pedido.estadoPago.Pendiente);
            }
            pedidoRepo.save(pedido);
        }

        return guardado;
    }

    public Double totalPagadoPorPedido(Long idPedido) {
        return pagoRepo.totalPagadoPorPedido(idPedido);
    }

    public List<DetallePago> listarPorPedido(Long idPedido) {
        return pagoRepo.findByPedidoIdPedido(idPedido);
    }

    public DetallePago buscarPorId(Long id) {
        return pagoRepo.findById(id).orElse(null);
    }

    public void eliminarPago(Long id) {
        DetallePago dp = pagoRepo.findById(id).orElse(null);
        if (dp != null) {
            Long idPedido = dp.getPedido().getIdPedido();
            pagoRepo.deleteById(id);

            // Recalcular estado del pedido tras eliminar pago
            Double totalPagado = pagoRepo.totalPagadoPorPedido(idPedido);
            Pedido pedido = pedidoRepo.findById(idPedido).orElse(null);
            if (pedido != null) {
                if (totalPagado >= pedido.getTotal()) {
                    pedido.setEstadoPago(Pedido.estadoPago.Pagado);
                    pedido.setFechaHoraCierre(LocalDateTime.now());
                    Mesa mesa = pedido.getMesa();
                    if (mesa != null) {
                        mesa.setEstado(Mesa.estadoMesa.Libre);
                        mesaRepo.save(mesa);
                    }
                } else {
                    pedido.setEstadoPago(Pedido.estadoPago.Pendiente);
                }
                pedidoRepo.save(pedido);
            }
        }
    }
}
