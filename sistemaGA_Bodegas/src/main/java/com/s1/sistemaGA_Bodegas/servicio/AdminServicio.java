package com.s1.sistemaGA_Bodegas.servicio;

import com.s1.sistemaGA_Bodegas.dto.solicitud.AdminSolicitud;
import com.s1.sistemaGA_Bodegas.dto.respuesta.AdminRespuesta;
import java.util.List;


public interface AdminServicio {

    AdminRespuesta crear(AdminSolicitud dto);

    AdminRespuesta obtenerPorId(Long id);

    AdminRespuesta obtenerPorUsuario(String usuario);

    List<AdminRespuesta> obtenerTodos();

    AdminRespuesta actualizar(Long id, AdminSolicitud dto);

    void eliminar(Long id);
}
