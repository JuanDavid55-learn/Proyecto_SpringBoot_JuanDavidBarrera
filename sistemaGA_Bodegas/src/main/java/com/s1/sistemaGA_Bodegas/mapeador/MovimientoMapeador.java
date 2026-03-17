package com.s1.sistemaGA_Bodegas.mapeador;

import com.s1.sistemaGA_Bodegas.dto.solicitud.MovimientoSolicitud;
import com.s1.sistemaGA_Bodegas.dto.respuesta.MovimientoProductoRespuesta;
import com.s1.sistemaGA_Bodegas.dto.respuesta.MovimientoRespuesta;
import com.s1.sistemaGA_Bodegas.modelo.*;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class MovimientoMapeador {

    public MovimientoRespuesta entidadADTO(Movimiento movimiento) {
        if (movimiento == null) return null;
        List<MovimientoProductoRespuesta> productos = movimiento.getProductos()
                .stream()
                .map(mp -> new MovimientoProductoRespuesta(
                        mp.getProducto().getId(),
                        mp.getProducto().getNombre(),
                        mp.getCantidad()
                ))
                .toList();
        return new MovimientoRespuesta(
                movimiento.getId(),
                movimiento.getFecha(),
                movimiento.getTipo(),
                movimiento.getResponsable().getId(),
                movimiento.getResponsable().getNombre(),
                movimiento.getBodegaOrigen()  != null ? movimiento.getBodegaOrigen().getId()     : null,
                movimiento.getBodegaOrigen()  != null ? movimiento.getBodegaOrigen().getNombre()  : null,
                movimiento.getBodegaDestino() != null ? movimiento.getBodegaDestino().getId()     : null,
                movimiento.getBodegaDestino() != null ? movimiento.getBodegaDestino().getNombre() : null,
                productos
        );
    }

    public Movimiento DTOAEntidad(MovimientoSolicitud dto, Empleado responsable,
                                  Bodega bodegaOrigen, Bodega bodegaDestino) {
        if (dto == null || responsable == null) return null;
        Movimiento movimiento = new Movimiento();
        movimiento.setFecha(LocalDateTime.now());
        movimiento.setTipo(dto.tipo());
        movimiento.setResponsable(responsable);
        movimiento.setBodegaOrigen(bodegaOrigen);
        movimiento.setBodegaDestino(bodegaDestino);
        return movimiento;
    }
}
