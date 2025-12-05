package com.gestion_mesas.demo.service.impl;

import com.gestion_mesas.demo.entity.Cliente;
import com.gestion_mesas.demo.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {
    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    public List<Cliente> listar() { return repository.findAll(); }

    public Cliente guardar(Cliente c) { return repository.save(c); }

    public Cliente buscarPorId(Long id) { return repository.findById(id).orElse(null); }

    public void eliminar(Long id) { repository.deleteById(id); }
}
