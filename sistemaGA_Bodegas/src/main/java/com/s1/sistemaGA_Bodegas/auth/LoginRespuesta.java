package com.s1.sistemaGA_Bodegas.auth;

public record LoginRespuesta(
        String token, String tipo, String usuario, String rol
) {
}
