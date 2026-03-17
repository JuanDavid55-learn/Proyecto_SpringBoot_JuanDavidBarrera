package com.s1.sistemaGA_Bodegas.dto.respuesta;

import com.s1.sistemaGA_Bodegas.modelo.TipoMovimiento;
import java.time.LocalDateTime;
import java.util.List;

public record MovimientoRespuesta(
        Long id, LocalDateTime fecha, TipoMovimiento tipo,
        Long responsableId, String responsableNombre,
        Long bodegaOrigenId, String bodegaOrigenNombre,
        Long bodegaDestinoId, String bodegaDestinoNombre,
        List<MovimientoProductoRespuesta> productos
) {
}
