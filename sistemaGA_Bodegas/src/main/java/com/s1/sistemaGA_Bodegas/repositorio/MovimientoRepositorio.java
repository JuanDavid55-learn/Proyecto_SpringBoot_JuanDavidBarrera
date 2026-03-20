package com.s1.sistemaGA_Bodegas.repositorio;

import com.s1.sistemaGA_Bodegas.modelo.Movimiento;
import com.s1.sistemaGA_Bodegas.modelo.TipoMovimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MovimientoRepositorio extends JpaRepository<Movimiento, Long>{

    List<Movimiento> findByTipo(TipoMovimiento tipo);

    List<Movimiento> findByResponsableId(Long empleadoId);

    List<Movimiento> findByBodegaOrigenId(Long bodegaId);

    List<Movimiento> findByBodegaDestinoId(Long bodegaId);

    List<Movimiento> findByFechaBetween(LocalDateTime desde, LocalDateTime hasta);

    List<Movimiento> findTop10ByFechaOrderByFechaDesc(LocalDateTime fecha);
}
