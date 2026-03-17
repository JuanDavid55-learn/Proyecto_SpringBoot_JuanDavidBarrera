package com.s1.sistemaGA_Bodegas.repositorio;

import com.s1.sistemaGA_Bodegas.modelo.Auditoria;
import com.s1.sistemaGA_Bodegas.modelo.TipoOperacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AuditoriaRepositorio extends JpaRepository<Auditoria, Long>{

    List<Auditoria> findByTipoOpc(TipoOperacion tipoOpc);

    List<Auditoria> findByResponsableId(Long adminId);

    List<Auditoria> findByFechaHoraBetween(LocalDateTime desde, LocalDateTime hasta);
}
