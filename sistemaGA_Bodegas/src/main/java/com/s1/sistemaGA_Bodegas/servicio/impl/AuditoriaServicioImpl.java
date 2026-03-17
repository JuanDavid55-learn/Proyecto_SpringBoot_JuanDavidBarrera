package com.s1.sistemaGA_Bodegas.servicio.impl;

import com.s1.sistemaGA_Bodegas.dto.solicitud.AuditoriaSolicitud;
import com.s1.sistemaGA_Bodegas.dto.respuesta.AuditoriaRespuesta;
import com.s1.sistemaGA_Bodegas.mapeador.AuditoriaMapeador;
import com.s1.sistemaGA_Bodegas.modelo.*;
import com.s1.sistemaGA_Bodegas.repositorio.AdminRepositorio;
import com.s1.sistemaGA_Bodegas.repositorio.AuditoriaRepositorio;
import com.s1.sistemaGA_Bodegas.repositorio.ProductoRepositorio;
import com.s1.sistemaGA_Bodegas.servicio.AuditoriaServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditoriaServicioImpl implements AuditoriaServicio{

    private final AuditoriaRepositorio auditoriaRepositorio;
    private final AdminRepositorio adminRepositorio;
    private final ProductoRepositorio productoRepositorio;
    private final AuditoriaMapeador auditoriaMapeador;

    @Override
    public AuditoriaRespuesta registrar(AuditoriaSolicitud dto) {
        Admin responsable = adminRepositorio.findById(Long.valueOf(dto.responsableId())).orElseThrow(() -> new RuntimeException("Admin no encontrado con ese id" ));
        Auditoria auditoria = auditoriaMapeador.DTOAEntidad(dto, responsable);
        for (var item : dto.cambios()) {
            Producto producto = null;
            if (item.productoId() != null) {
                producto = productoRepositorio.findById(item.productoId()).orElseThrow(() -> new RuntimeException("Producto no encontrado con ese id"));
            }
            AuditoriaCambio cambio = new AuditoriaCambio();
            cambio.setAuditoria(auditoria);
            cambio.setProducto(producto);
            cambio.setCampo(item.campo());
            cambio.setCategoriaAnterior(item.categoriaAnterior());
            cambio.setCategoriaNuevo(item.categoriaNuevo());
            cambio.setStockAnterior(item.stockAnterior());
            cambio.setStockNuevo(item.stockNuevo());
            cambio.setPrecioAnterior(item.precioAnterior());
            cambio.setPrecioNuevo(item.precioNuevo());
            auditoria.getCambios().add(cambio);
        }
        return auditoriaMapeador.entidadADTO(auditoriaRepositorio.save(auditoria));
    }

    @Override
    public AuditoriaRespuesta obtenerPorId(Long id) {
        Auditoria auditoria = auditoriaRepositorio.findById(id).orElseThrow(() -> new RuntimeException("Auditoria no encontrada con ese id"));
        return auditoriaMapeador.entidadADTO(auditoria);
    }

    @Override
    public List<AuditoriaRespuesta> obtenerTodos() {
        List<Auditoria> auditorias = auditoriaRepositorio.findAll();
        return auditorias.stream().map(auditoriaMapeador::entidadADTO).toList();
    }

    @Override
    public List<AuditoriaRespuesta> obtenerPorTipoOperacion(TipoOperacion tipoOpc) {
        List<Auditoria> auditorias = auditoriaRepositorio.findByTipoOpc(tipoOpc);
        return auditorias.stream().map(auditoriaMapeador::entidadADTO).toList();
    }

    @Override
    public List<AuditoriaRespuesta> obtenerPorAdmin(Long adminId) {
        List<Auditoria> auditorias = auditoriaRepositorio.findByResponsableId(adminId);
        return auditorias.stream().map(auditoriaMapeador::entidadADTO).toList();
    }

    @Override
    public List<AuditoriaRespuesta> obtenerPorRangoFechas(LocalDateTime desde, LocalDateTime hasta) {
        List<Auditoria> auditorias = auditoriaRepositorio.findByFechaHoraBetween(desde,hasta);
        return auditorias.stream().map(auditoriaMapeador::entidadADTO).toList();
    }
}
