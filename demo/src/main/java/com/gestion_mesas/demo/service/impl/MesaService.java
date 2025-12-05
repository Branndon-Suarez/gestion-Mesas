package com.gestion_mesas.demo.service.impl;

import com.gestion_mesas.demo.entity.Mesa;
import com.gestion_mesas.demo.repository.MesaRepository;
import com.gestion_mesas.demo.service.IMesaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MesaService implements IMesaService {
    public final MesaRepository repository;
    public MesaService(MesaRepository repository) {
        this.repository = repository;
    }

    public List<Mesa> listarMesas() {
        return repository.findAll();
    }

    public Mesa guardarMesa(Mesa mesa) {
        return repository.save(mesa);
    }

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    public Mesa buscarById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void eliminarMesa(Long id) {
        repository.deleteById(id);
    }
}
