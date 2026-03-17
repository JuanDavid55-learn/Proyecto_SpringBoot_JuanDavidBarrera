package com.s1.sistemaGA_Bodegas.repositorio;

import com.s1.sistemaGA_Bodegas.modelo.AuditoriaCambio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AuditoriaCambioRepositorio extends JpaRepository<AuditoriaCambio, Long>{

    List<AuditoriaCambio> findByAuditoriaId(Long auditoriaId);

    List<AuditoriaCambio> findByProductoId(Long productoId);

    List<AuditoriaCambio> findByProductoIdAndCampo(Long productoId, String campo);
}
