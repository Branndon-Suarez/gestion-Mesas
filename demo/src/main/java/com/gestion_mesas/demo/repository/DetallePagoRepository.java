package com.gestion_mesas.demo.repository;

import com.gestion_mesas.demo.entity.DetallePago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DetallePagoRepository extends JpaRepository<DetallePago, Long> {
    @Query("SELECT COALESCE(SUM(dp.montoPago), 0) FROM DetallePago dp WHERE dp.pedido.idPedido = :idPedido")
    Double totalPagadoPorPedido(@Param("idPedido") Long idPedido);

    List<DetallePago> findByPedidoIdPedido(Long idPedido);
}
