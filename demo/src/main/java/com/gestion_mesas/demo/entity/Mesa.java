package com.gestion_mesas.demo.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Mesa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mesa", unique = true)
    private Long idMesa;

    @Column(name = "numero", unique = true, nullable = false)
    private Integer numero;

    @Column(name = "capacidad", nullable = false)
    private Integer capacidad;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private estadoMesa estado;

    @OneToMany(mappedBy = "mesa")
    private List<Pedido> listaPedidos;


    public enum estadoMesa {
        LIBRE, OCUPADA, SUCIA, RESERVADA
    }

    // Getters and Setters
    public Long getIdMesa() {
        return idMesa;
    }
    public void setIdMesa(Long idMesa) {
        this.idMesa = idMesa;
    }

    public Integer getNumero() {
        return numero;
    }
    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Integer getCapacidad() {
        return capacidad;
    }
    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    public List<Pedido> getListaPedidos() {
        return listaPedidos;
    }
    public void setListaPedidos(List<Pedido> listaPedidos) {
        this.listaPedidos = listaPedidos;
    }

    public estadoMesa getEstado() {
        return estado;
    }
    public void setEstado(estadoMesa estado) {
        this.estado = estado;
    }
}