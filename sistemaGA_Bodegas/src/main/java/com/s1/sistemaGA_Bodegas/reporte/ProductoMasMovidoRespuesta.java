package com.s1.sistemaGA_Bodegas.reporte;

public record ProductoMasMovidoRespuesta(
        Long productoId, String productoNombre, String categoria,
        Long stockActual, Long totalUnidadesMovidas
) {
}
