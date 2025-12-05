package com.gestion_mesas.demo.entity;

import jakarta.persistence.*;

@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Long idCliente;

    @Column(name = "num_documento", unique = true, nullable = false)
    private Long numDocumento;

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @Column(name = "telefono", unique = true, nullable = false, length = 20)
    private String telefono;

    @Column(name = "email", unique = true, nullable = false, length = 100)
    private String email;

    // Getters y Setters
    public Long getIdCliente() { return idCliente; }
    public void setIdCliente(Long idCliente) { this.idCliente = idCliente; }

    public Long getNumDocumento() { return numDocumento; }
    public void setNumDocumento(Long numDocumento) { this.numDocumento = numDocumento; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
