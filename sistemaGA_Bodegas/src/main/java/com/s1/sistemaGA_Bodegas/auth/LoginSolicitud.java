package com.s1.sistemaGA_Bodegas.auth;

import jakarta.validation.constraints.*;

public record LoginSolicitud(
        @NotBlank(message = "El usuario no puede estar vacío.")
        @Size(min = 2, max = 60, message = "El usuario debe tener entre 2 y 60 caracteres")
        String usuario,

        @NotBlank(message = "La contraseña no puede estar vacía.")
        @Size(min = 4, max = 255, message = "La contraseña debe tener entre 4 y 255 caracteres")
        String contrasena
) {
}
