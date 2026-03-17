package com.s1.sistemaGA_Bodegas.dto.respuesta;

public record AuditoriaCambioRespuesta(
        Long id, Long productoId, String productoNombre, String campo,
        String categoriaAnterior, String categoriaNuevo,
        String stockAnterior, String stockNuevo,
        String precioAnterior, String precioNuevo
) {
}
