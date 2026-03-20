package com.s1.sistemaGA_Bodegas.reporte;

public record ReporteMovimientosRespuesta(
        long totalMovimientos,
        long totalEntradas,
        long totalSalidas,
        long totalTransferencias
) {
}
