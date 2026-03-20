package com.s1.sistemaGA_Bodegas.reporte;

import com.s1.sistemaGA_Bodegas.modelo.TipoMovimiento;
import com.s1.sistemaGA_Bodegas.repositorio.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ReporteServicio {
    private final BodegaRepositorio bodegaRepositorio;
    private final ProductoRepositorio productoRepositorio;
    private final MovimientoRepositorio movimientoRepositorio;
    private final AuditoriaRepositorio auditoriaRepositorio;
    private final MovimientoProductoRepositorio movimientoProductoRepositorio;

    @Transactional(readOnly = true)
    public ResumenGeneralRespuesta generarResumen() {
        Long totalBodegas     = bodegaRepositorio.count();
        Long totalProductos   = productoRepositorio.count();
        Long totalMovimientos = movimientoRepositorio.count();
        Long totalAuditorias  = auditoriaRepositorio.count();
        Map<Long, StockPorBodegaRespuesta> stockMap = new LinkedHashMap<>();

        for (Object[] row : movimientoProductoRepositorio.findStockMovidoPorBodegaOrigen()) {
            Long id          = (Long)   row[0];
            String nombre    = (String) row[1];
            String ubicacion = (String) row[2];
            Long movs        = (Long)   row[3];
            Long unidades    = (Long)   row[4];
            stockMap.put(id, new StockPorBodegaRespuesta(id, nombre, ubicacion, movs, unidades));
        }

        for (Object[] row : movimientoProductoRepositorio.findStockMovidoPorBodegaDestino()) {
            Long id          = (Long)   row[0];
            String nombre    = (String) row[1];
            String ubicacion = (String) row[2];
            Long movs        = (Long)   row[3];
            Long unidades    = (Long)   row[4];
            stockMap.merge(id,
                    new StockPorBodegaRespuesta(id, nombre, ubicacion, movs, unidades),
                    (existing, nuevo) -> new StockPorBodegaRespuesta(
                            existing.bodegaId(),
                            existing.bodegaNombre(),
                            existing.bodegaUbicacion(),
                            existing.totalMovimientos() + nuevo.totalMovimientos(),
                            existing.totalUnidadesMovidas() + nuevo.totalUnidadesMovidas()
                    )
            );
        }

        List<StockPorBodegaRespuesta> stockPorBodega = new ArrayList<>(stockMap.values());

        List<ProductoMasMovidoRespuesta> productosMasMovidos =
                movimientoProductoRepositorio.findProductosMasMovidos()
                        .stream()
                        .limit(10)
                        .map(row -> new ProductoMasMovidoRespuesta(
                                (Long)   row[0],
                                (String) row[1],
                                (String) row[2],
                                (Long)   row[3],
                                (Long)   row[4]
                        ))
                        .toList();

        return new ResumenGeneralRespuesta(
                totalBodegas,
                totalProductos,
                totalMovimientos,
                totalAuditorias,
                stockPorBodega,
                productosMasMovidos
        );
    }

    // reporte Movimientos x tipo
    @Transactional(readOnly = true)
    public ReporteMovimientosRespuesta generarReporteMovimientos() {
        long total         = movimientoRepositorio.count();
        long entradas      = movimientoRepositorio.countByTipo(TipoMovimiento.ENTRADA);
        long salidas       = movimientoRepositorio.countByTipo(TipoMovimiento.SALIDA);
        long transferencias = movimientoRepositorio.countByTipo(TipoMovimiento.TRANSFERENCIA);

        return new ReporteMovimientosRespuesta(total, entradas, salidas, transferencias);
    }
}
