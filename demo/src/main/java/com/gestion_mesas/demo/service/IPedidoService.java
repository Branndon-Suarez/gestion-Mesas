package com.gestion_mesas.demo.service;

import com.gestion_mesas.demo.entity.Mesa;
import com.gestion_mesas.demo.entity.Pedido;

import java.util.List;

public interface IPedidoService {
    List<Pedido> listarPedidos();

    Pedido obtenerPedidoActivo(Long idMesa);

    void guardarPedido(Mesa mesa);
}
