package com.s1.sistemaGA_Bodegas.dto.solicitud;

import jakarta.validation.constraints.*;

public record MovimientoProductoSolicitud(

        @NotNull(message = "El producto es obligatorio")
        Long productoId,

        @NotNull(message = "La cantidad es obligatoria")
        @Min(value = 1, message = "La cantidad debe ser mínimo 1")
        Long cantidad
) {
}
