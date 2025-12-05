package com.gestion_mesas.demo.repository;

import com.gestion_mesas.demo.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    Optional<Pedido> findByMesaIdMesaAndEstadoPago(Long idMesa, Pedido.estadoPago estadoPago);
}
