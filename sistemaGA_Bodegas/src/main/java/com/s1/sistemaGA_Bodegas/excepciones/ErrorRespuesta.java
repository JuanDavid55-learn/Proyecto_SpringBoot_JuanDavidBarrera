package com.s1.sistemaGA_Bodegas.excepciones;

import java.time.LocalDateTime;

public record ErrorRespuesta(
        LocalDateTime timestamp, int status, String message, String errorCode
) {
}
