package com.gestion_mesas.demo.service.impl;

import com.gestion_mesas.demo.entity.Mesa;
import com.gestion_mesas.demo.entity.Pedido;
import com.gestion_mesas.demo.repository.PedidoRepository;
import com.gestion_mesas.demo.service.IPedidoService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PedidoService implements IPedidoService {
    public final PedidoRepository repository;
    public PedidoService(PedidoRepository repository) {
        this.repository = repository;
    }

    public List<Pedido> listarPedidos() {
        return repository.findAll();
    }

    public Pedido obtenerPedidoActivo(Long idMesa) {
        return repository.findByMesaIdMesaAndEstadoPago(idMesa, Pedido.estadoPago.PENDIENTE).orElse(null);
    }

    public void guardarPedido(Mesa mesa) {
        Pedido nuevo = new Pedido();
        nuevo.setFechaHoraApertura(LocalDateTime.now());
        nuevo.setMesa(mesa);
        nuevo.setTotal(0.0);
        nuevo.setEstadoPago(Pedido.estadoPago.PENDIENTE);

        mesa.setEstado(Mesa.estadoMesa.OCUPADA);

        repository.save(nuevo);
    }

    public Pedido guardarPedido(Pedido pedido) {
        return repository.save(pedido);
    }

    public Pedido buscarPorId(Long idPedido) {
        return repository.findById(idPedido).orElse(null);
    }
}
