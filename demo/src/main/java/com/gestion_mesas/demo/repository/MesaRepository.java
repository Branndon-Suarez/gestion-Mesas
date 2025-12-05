package com.gestion_mesas.demo.repository;

import com.gestion_mesas.demo.entity.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MesaRepository extends JpaRepository<Mesa, Long> {
    boolean existsById(Long idMesa);
}
