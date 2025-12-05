package com.gestion_mesas.demo.service.impl;

import com.gestion_mesas.demo.entity.DetallePedido;
import com.gestion_mesas.demo.repository.DetallePedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetallePedidoService {
    private final DetallePedidoRepository repository;

    public DetallePedidoService(DetallePedidoRepository repository) {
        this.repository = repository;
    }

    public List<DetallePedido> listarPorPedido(Long idPedido) {
        return repository.findByPedidoIdPedido(idPedido);
    }

    public DetallePedido guardarDetalle(DetallePedido detalle) {
        detalle.setSubtotal(detalle.getCantidad() * detalle.getPrecioUnitario());
        return repository.save(detalle);
    }

    public DetallePedido buscarPorId(Long idDetalle) {
        return repository.findById(idDetalle).orElse(null);
    }

    public void eliminarDetalle(Long id) {
        repository.deleteById(id);
    }
}
