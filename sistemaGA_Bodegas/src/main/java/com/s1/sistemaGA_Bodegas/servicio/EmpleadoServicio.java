package com.s1.sistemaGA_Bodegas.servicio;

import com.s1.sistemaGA_Bodegas.dto.solicitud.EmpleadoSolicitud;
import com.s1.sistemaGA_Bodegas.dto.respuesta.EmpleadoRespuesta;
import java.util.List;

public interface EmpleadoServicio {

    EmpleadoRespuesta crear(EmpleadoSolicitud dto);

    EmpleadoRespuesta obtenerPorId(Long id);

    EmpleadoRespuesta obtenerPorUsuario(String usuario);

    List<EmpleadoRespuesta> obtenerTodos();

    EmpleadoRespuesta actualizar(Long id, EmpleadoSolicitud dto);

    void eliminar(Long id);
}
