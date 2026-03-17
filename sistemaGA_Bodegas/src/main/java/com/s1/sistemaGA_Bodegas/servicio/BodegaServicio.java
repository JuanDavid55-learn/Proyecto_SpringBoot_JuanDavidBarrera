package com.s1.sistemaGA_Bodegas.servicio;

import com.s1.sistemaGA_Bodegas.dto.solicitud.BodegaSolicitud;
import com.s1.sistemaGA_Bodegas.dto.respuesta.BodegaRespuesta;
import java.util.List;

public interface BodegaServicio {

    BodegaRespuesta crear(BodegaSolicitud dto);

    BodegaRespuesta obtenerPorId(Long id);

    List<BodegaRespuesta> obtenerTodos();

    List<BodegaRespuesta> obtenerPorEncargado(Long adminId);

    BodegaRespuesta actualizar(Long id, BodegaSolicitud dto);

    void eliminar(Long id);
}
