package com.s1.sistemaGA_Bodegas.reporte;

import java.util.List;

public record ResumenGeneralRespuesta(
        Long totalBodegas, Long totalProductos, Long totalMovimientos,
        Long totalAuditorias, List<StockPorBodegaRespuesta> stockPorBodega,
        List<ProductoMasMovidoRespuesta> productosMasMovidos
) {
}
