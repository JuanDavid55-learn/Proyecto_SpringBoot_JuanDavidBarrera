package com.s1.sistemaGA_Bodegas.reporte;

public record StockPorBodegaRespuesta(
        Long bodegaId, String bodegaNombre, String bodegaUbicacion,
        Long totalMovimientos, Long totalUnidadesMovidas
) {
}
