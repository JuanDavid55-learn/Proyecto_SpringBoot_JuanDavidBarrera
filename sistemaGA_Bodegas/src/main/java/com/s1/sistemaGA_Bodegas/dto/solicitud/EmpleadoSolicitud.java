package com.s1.sistemaGA_Bodegas.dto.solicitud;

import jakarta.validation.constraints.*;

public record EmpleadoSolicitud(

        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 100, message = "El nombre no puede superar 100 caracteres")
        String nombre,

        @NotBlank(message = "El documento es obligatorio")
        @Size(max = 20, message = "El documento no puede superar 20 caracteres")
        String documento,

        @NotNull(message = "La edad es obligatoria")
        Long edad,

        @NotBlank(message = "El correo es obligatorio")
        @Email(message = "Error, el correo no tiene un formato válido")
        @Size(max = 150, message = "El correo no puede superar 150 caracteres")
        String correo,

        @NotBlank(message = "El usuario es obligatorio")
        @Size(min = 4, max = 60, message = "El usuario debe tener entre 4 y 60 caracteres")
        String usuario,

        @NotBlank(message = "La contraseña es obligatoria")
        @Size(min = 4, message = "La contraseña debe tener mínimo 4 caracteres")
        String contrasena
) {
}
