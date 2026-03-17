package com.s1.sistemaGA_Bodegas.dto.respuesta;

public record EmpleadoRespuesta(
        Long id, String nombre, String documento,
        Long edad, String correo, String usuario
) {
}
