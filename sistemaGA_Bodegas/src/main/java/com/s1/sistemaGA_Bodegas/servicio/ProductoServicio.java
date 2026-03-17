package com.s1.sistemaGA_Bodegas.servicio;

import com.s1.sistemaGA_Bodegas.dto.solicitud.ProductoSolicitud;
import com.s1.sistemaGA_Bodegas.dto.respuesta.ProductoRespuesta;
import java.util.List;

public interface ProductoServicio {

    ProductoRespuesta crear(ProductoSolicitud dto);

    ProductoRespuesta obtenerPorId(Long id);

    List<ProductoRespuesta> obtenerTodos();

    List<ProductoRespuesta> obtenerPorCategoria(String categoria);

    List<ProductoRespuesta> obtenerConStockBajoMinimo(Long minimo);

    ProductoRespuesta actualizar(Long id, ProductoSolicitud dto);

    void eliminar(Long id);
}
