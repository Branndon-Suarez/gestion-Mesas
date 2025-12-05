package com.gestion_mesas.demo.service.impl;

import com.gestion_mesas.demo.entity.Producto;
import com.gestion_mesas.demo.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {
    private final ProductoRepository repository;

    public ProductoService(ProductoRepository repository) {
        this.repository = repository;
    }

    public List<Producto> listarProductos() {
        return repository.findAll();
    }

    public Producto guardarProducto(Producto producto) {
        return repository.save(producto);
    }

    public Producto buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void eliminarProducto(Long id) {
        repository.deleteById(id);
    }
}
