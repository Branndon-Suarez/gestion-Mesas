package com.gestion_mesas.demo.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class DetallePago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_pago")
    private Long idDetallePago;

    @ManyToOne
    @JoinColumn(name = "id_pedido_fk", nullable = false)
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "id_cliente_fk", nullable = false)
    private Cliente cliente;

    @Column(name = "fecha_pago", nullable = false)
    private LocalDateTime fechaPago;

    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pago")
    private MetodoPago metodoPago;

    @Column(name = "monto_pago", nullable = false)
    private Double montoPago;

    public enum MetodoPago {
        EFECTIVO,
        TARJETA_CREDITO,
        TARJETA_DEBITO,
        NEQUI
    }

    // Getters y Setters
    public Long getIdDetallePago() { return idDetallePago; }
    public void setIdDetallePago(Long idDetallePago) { this.idDetallePago = idDetallePago; }

    public Pedido getPedido() { return pedido; }
    public void setPedido(Pedido pedido) { this.pedido = pedido; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public LocalDateTime getFechaPago() { return fechaPago; }
    public void setFechaPago(LocalDateTime fechaPago) { this.fechaPago = fechaPago; }

    public MetodoPago getMetodoPago() { return metodoPago; }
    public void setMetodoPago(MetodoPago metodoPago) { this.metodoPago = metodoPago; }

    public Double getMontoPago() { return montoPago; }
    public void setMontoPago(Double montoPago) { this.montoPago = montoPago; }
}
