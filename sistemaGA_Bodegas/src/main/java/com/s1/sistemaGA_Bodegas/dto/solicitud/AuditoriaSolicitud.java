package com.s1.sistemaGA_Bodegas.dto.solicitud;

import com.s1.sistemaGA_Bodegas.modelo.TipoOperacion;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;

public record AuditoriaSolicitud(

        @NotNull(message = "El tipo de operación es obligatorio")
        TipoOperacion tipoOpc,

        @NotNull(message = "El responsable es obligatorio")
        Integer responsableId,

        @NotEmpty(message = "Debe registrar al menos un cambio")
        @Valid
        List<AuditoriaCambioSolicitud> cambios
) {
}
