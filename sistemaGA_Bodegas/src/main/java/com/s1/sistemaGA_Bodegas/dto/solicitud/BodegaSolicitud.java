package com.s1.sistemaGA_Bodegas.dto.solicitud;

import jakarta.validation.constraints.*;

public record BodegaSolicitud(

        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 100, message = "El nombre no puede superar 100 caracteres")
        String nombre,

        @NotBlank(message = "La ubicacion es obligatoria")
        @Size(max = 100, message = "La ubicacion no puede superar 100 caracteres")
        String ubicacion,

        @NotNull(message = "La capacidad es obligatoria")
        Long capacidad,

        @NotNull(message = "El encargado es obligatorio")
        Long encargadoId
) {
}
