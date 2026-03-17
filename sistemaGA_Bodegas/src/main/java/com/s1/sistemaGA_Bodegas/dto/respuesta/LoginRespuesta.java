package com.s1.sistemaGA_Bodegas.dto.respuesta;

public record LoginRespuesta(
        String token, String tipo, String usuario, String rol
) {
}
