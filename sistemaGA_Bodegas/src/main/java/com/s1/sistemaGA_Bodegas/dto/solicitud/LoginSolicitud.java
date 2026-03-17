package com.s1.sistemaGA_Bodegas.dto.solicitud;

import jakarta.validation.constraints.*;

public record LoginSolicitud(

        @NotBlank(message = "El usuario no puede estar vacio.")
        @Size(min = 2, max = 60, message = "Error, el rango del usuario debe estar entre 2 y 60 caracteres")
        String usuario,

        @NotBlank(message = "La contraseña no puede estar vacia.")
        @Size(min = 4, max = 255, message = "Error, el rango de la contraseña debe estar entre 4 y 255 caracteres")
        String contrasena
) {
}
