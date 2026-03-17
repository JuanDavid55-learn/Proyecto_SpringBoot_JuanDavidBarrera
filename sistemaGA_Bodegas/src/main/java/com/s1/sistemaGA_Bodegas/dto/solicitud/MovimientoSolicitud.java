package com.s1.sistemaGA_Bodegas.dto.solicitud;

import com.s1.sistemaGA_Bodegas.modelo.TipoMovimiento;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;

public record MovimientoSolicitud(

        @NotNull(message = "El tipo de movimiento es obligatorio")
        TipoMovimiento tipo,

        @NotNull(message = "El responsable es obligatorio")
        Integer responsableId,

        Integer bodegaOrigenId,

        Integer bodegaDestinoId,

        @NotEmpty(message = "Debe incluir al menos un producto")
        @Valid
        List<MovimientoProductoSolicitud> productos
) {
}
