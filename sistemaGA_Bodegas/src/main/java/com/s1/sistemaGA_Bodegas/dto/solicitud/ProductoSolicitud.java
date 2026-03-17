package com.s1.sistemaGA_Bodegas.dto.solicitud;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record ProductoSolicitud(

        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 100, message = "El nombre no puede superar 100 caracteres")
        String nombre,

        @NotBlank(message = "La categoría es obligatoria")
        @Size(max = 80, message = "La categoría no puede superar 80 caracteres")
        String categoria,

        @NotNull(message = "El stock es obligatorio")
        @Min(value = 0, message = "El stock no puede ser negativo")
        Long stock,

        @NotNull(message = "El precio es obligatorio")
        @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
        @Digits(integer = 12, fraction = 2, message = "El precio debe tener máximo 12 enteros y 2 decimales")
        BigDecimal precio
) {
}
