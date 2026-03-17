package com.s1.sistemaGA_Bodegas.dto.respuesta;

public record BodegaRespuesta(
        Long id, String nombre, String ubicacion, Long capacidad,
        Long encargadoId, String encargadoNombre
) {
}
