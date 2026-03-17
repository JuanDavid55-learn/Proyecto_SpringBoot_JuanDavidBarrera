package com.s1.sistemaGA_Bodegas.servicio;

import com.s1.sistemaGA_Bodegas.dto.solicitud.AuditoriaSolicitud;
import com.s1.sistemaGA_Bodegas.dto.respuesta.AuditoriaRespuesta;
import com.s1.sistemaGA_Bodegas.modelo.TipoOperacion;
import java.time.LocalDateTime;
import java.util.List;

public interface AuditoriaServicio {

    AuditoriaRespuesta registrar(AuditoriaSolicitud dto);

    AuditoriaRespuesta obtenerPorId(Long id);

    List<AuditoriaRespuesta> obtenerTodos();

    List<AuditoriaRespuesta> obtenerPorTipoOperacion(TipoOperacion tipoOpc);

    List<AuditoriaRespuesta> obtenerPorAdmin(Long adminId);

    List<AuditoriaRespuesta> obtenerPorRangoFechas(LocalDateTime desde, LocalDateTime hasta);
}
