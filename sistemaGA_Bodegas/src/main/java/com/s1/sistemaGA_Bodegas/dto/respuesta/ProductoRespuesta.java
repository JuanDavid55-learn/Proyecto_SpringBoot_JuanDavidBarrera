package com.s1.sistemaGA_Bodegas.dto.respuesta;

import java.math.BigDecimal;

public record ProductoRespuesta(
        Long id, String nombre, String categoria, Long stock, BigDecimal precio
) {
}
