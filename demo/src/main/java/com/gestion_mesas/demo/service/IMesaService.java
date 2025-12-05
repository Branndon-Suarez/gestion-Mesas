package com.gestion_mesas.demo.service;

import com.gestion_mesas.demo.entity.Mesa;

import java.util.List;

public interface IMesaService {
    List<Mesa> listarMesas();

    Mesa guardarMesa(Mesa mesa);
    boolean existsById(Long id);

    Mesa buscarById(Long id);

    void eliminarMesa(Long id);
}
