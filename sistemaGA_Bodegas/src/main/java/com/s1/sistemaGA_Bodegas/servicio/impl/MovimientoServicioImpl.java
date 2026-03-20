package com.s1.sistemaGA_Bodegas.servicio.impl;

import com.s1.sistemaGA_Bodegas.dto.solicitud.MovimientoSolicitud;
import com.s1.sistemaGA_Bodegas.dto.respuesta.MovimientoRespuesta;
import com.s1.sistemaGA_Bodegas.mapeador.MovimientoMapeador;
import com.s1.sistemaGA_Bodegas.modelo.*;
import com.s1.sistemaGA_Bodegas.repositorio.*;
import com.s1.sistemaGA_Bodegas.servicio.MovimientoServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovimientoServicioImpl implements MovimientoServicio{

    private final MovimientoRepositorio movimientoRepositorio;
    private final EmpleadoRepositorio empleadoRepositorio;
    private final BodegaRepositorio bodegaRepositorio;
    private final ProductoRepositorio productoRepositorio;
    private final MovimientoMapeador movimientoMapeador;

    @Override
    public MovimientoRespuesta registrar(MovimientoSolicitud dto) {
        Empleado responsable = empleadoRepositorio.findById(Long.valueOf(dto.responsableId())).orElseThrow(() -> new RuntimeException("Empleado no encontrado con ese id"));
        Bodega bodegaOrigen = null;
        if (dto.bodegaOrigenId() != null) {
            bodegaOrigen = bodegaRepositorio.findById(Long.valueOf(dto.bodegaOrigenId())).orElseThrow(() -> new RuntimeException("Bodega origen no encontrada con ese id" ));
        }
        Bodega bodegaDestino = null;
        if (dto.bodegaDestinoId() != null) {
            bodegaDestino = bodegaRepositorio.findById(Long.valueOf(dto.bodegaDestinoId())).orElseThrow(()-> new RuntimeException("Bodega destino no encontrada con ese id" ));
        }
        Movimiento movimiento = movimientoMapeador.DTOAEntidad(dto, responsable, bodegaOrigen, bodegaDestino);
        for (var item : dto.productos()) {
            Producto producto = productoRepositorio.findById(item.productoId()).orElseThrow(() -> new RuntimeException("Producto no encontrado con ese id"));
            MovimientoProducto mp = new MovimientoProducto();
            mp.setMovimiento(movimiento);
            mp.setProducto(producto);
            mp.setCantidad(item.cantidad());
            movimiento.getProductos().add(mp);
        }
        return movimientoMapeador.entidadADTO(movimientoRepositorio.save(movimiento));
    }

    @Override
    public MovimientoRespuesta obtenerPorId(Long id) {
        Movimiento movimiento = movimientoRepositorio.findById(id).orElseThrow(() -> new RuntimeException("Movimiento no encontrado con ese id"));
        return movimientoMapeador.entidadADTO(movimiento);
    }

    @Override
    public List<MovimientoRespuesta> obtenerTodos() {
        List<Movimiento> movimientos = movimientoRepositorio.findAll();
        return movimientos.stream().map(movimientoMapeador::entidadADTO).toList();
    }

    @Override
    public List<MovimientoRespuesta> obtenerPorTipo(TipoMovimiento tipo) {
        List<Movimiento> movimientos = movimientoRepositorio.findByTipo(tipo);
        return movimientos.stream().map(movimientoMapeador::entidadADTO).toList();
    }

    @Override
    public List<MovimientoRespuesta> obtenerPorEmpleado(Long empleadoId) {
        List<Movimiento> movimientos = movimientoRepositorio.findByResponsableId(empleadoId);
        return movimientos.stream().map(movimientoMapeador::entidadADTO).toList();
    }

    @Override
    public List<MovimientoRespuesta> obtenerPorBodegaOrigen(Long bodegaId) {
        List<Movimiento> movimientos = movimientoRepositorio.findByBodegaOrigenId(bodegaId);
        return movimientos.stream().map(movimientoMapeador::entidadADTO).toList();
    }

    @Override
    public List<MovimientoRespuesta> obtenerPorBodegaDestino(Long bodegaId) {
        List<Movimiento> movimientos = movimientoRepositorio.findByBodegaDestinoId(bodegaId);
        return movimientos.stream().map(movimientoMapeador::entidadADTO).toList();
    }

    @Override
    public List<MovimientoRespuesta> obtenerPorRangoFechas(LocalDateTime desde, LocalDateTime hasta) {
        List<Movimiento> movimientos = movimientoRepositorio.findByFechaBetween(desde,hasta);
        return movimientos.stream().map(movimientoMapeador::entidadADTO).toList();
    }

    @Override
    public List<MovimientoRespuesta> listarRecientes(LocalDateTime fecha) {
        List<Movimiento> movimientos = movimientoRepositorio.findTop10ByFechaOrderByFechaDesc(fecha);
        return movimientos.stream().map(movimientoMapeador::entidadADTO).toList();
    }
}
