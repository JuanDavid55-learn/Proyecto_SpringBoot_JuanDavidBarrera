package com.s1.sistemaGA_Bodegas.servicio;

import com.s1.sistemaGA_Bodegas.dto.solicitud.MovimientoSolicitud;
import com.s1.sistemaGA_Bodegas.dto.respuesta.MovimientoRespuesta;
import com.s1.sistemaGA_Bodegas.modelo.TipoMovimiento;

import java.time.LocalDateTime;
import java.util.List;

public interface MovimientoServicio {

    MovimientoRespuesta registrar(MovimientoSolicitud dto);

    MovimientoRespuesta obtenerPorId(Long id);

    List<MovimientoRespuesta> obtenerTodos();

    List<MovimientoRespuesta> obtenerPorTipo(TipoMovimiento tipo);

    List<MovimientoRespuesta> obtenerPorEmpleado(Long empleadoId);

    List<MovimientoRespuesta> obtenerPorBodegaOrigen(Long bodegaId);

    List<MovimientoRespuesta> obtenerPorBodegaDestino(Long bodegaId);

    List<MovimientoRespuesta> obtenerPorRangoFechas(LocalDateTime desde, LocalDateTime hasta);

    List<MovimientoRespuesta> obtenerRecientes();
}
