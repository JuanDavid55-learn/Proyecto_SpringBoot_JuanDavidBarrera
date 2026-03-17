package com.s1.sistemaGA_Bodegas.dto.solicitud;

import jakarta.validation.constraints.*;

public record AuditoriaCambioSolicitud(

        Long productoId,

        @NotBlank(message = "El campo es obligatorio")
        @Size(max = 60)
        String campo,

        String categoriaAnterior,
        String categoriaNuevo,

        String stockAnterior,
        String stockNuevo,

        String precioAnterior,
        String precioNuevo
) {
}
