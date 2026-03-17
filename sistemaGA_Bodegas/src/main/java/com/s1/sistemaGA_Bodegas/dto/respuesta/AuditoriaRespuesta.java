package com.s1.sistemaGA_Bodegas.dto.respuesta;

import com.s1.sistemaGA_Bodegas.modelo.TipoOperacion;
import java.time.LocalDateTime;
import java.util.List;

public record AuditoriaRespuesta(
        Long id, TipoOperacion tipoOpc, LocalDateTime fechaHora,
        Long responsableId, String responsableNombre,
        List<AuditoriaCambioRespuesta> cambios
) {
}
