package com.s1.sistemaGA_Bodegas.servicio.impl;

import com.s1.sistemaGA_Bodegas.dto.solicitud.BodegaSolicitud;
import com.s1.sistemaGA_Bodegas.dto.respuesta.BodegaRespuesta;
import com.s1.sistemaGA_Bodegas.mapeador.BodegaMapeador;
import com.s1.sistemaGA_Bodegas.modelo.Admin;
import com.s1.sistemaGA_Bodegas.modelo.Bodega;
import com.s1.sistemaGA_Bodegas.repositorio.AdminRepositorio;
import com.s1.sistemaGA_Bodegas.repositorio.BodegaRepositorio;
import com.s1.sistemaGA_Bodegas.servicio.BodegaServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BodegaServicioImpl implements BodegaServicio {

    private final BodegaRepositorio bodegaRepositorio;
    private final AdminRepositorio adminRepositorio;
    private final BodegaMapeador bodegaMapeador;

    @Override
    public BodegaRespuesta crear(BodegaSolicitud dto) {
        Admin encargado = adminRepositorio.findById(dto.encargadoId()).orElseThrow(() -> new RuntimeException("Admin no encontrado con ese id"));
        Bodega bodega = bodegaMapeador.DTOAEntidad(dto, encargado);
        return bodegaMapeador.entidadADTO(bodegaRepositorio.save(bodega));
    }

    @Override
    public BodegaRespuesta obtenerPorId(Long id) {
        Bodega bodega = bodegaRepositorio.findById(id).orElseThrow(() -> new RuntimeException("Bodega no encontrada con ese id"));
        return bodegaMapeador.entidadADTO(bodega);
    }

    @Override
    public List<BodegaRespuesta> obtenerTodos() {
        List<Bodega> bodegas = bodegaRepositorio.findAll();
        return bodegas.stream().map(bodegaMapeador::entidadADTO).toList();
    }

    @Override
    public List<BodegaRespuesta> obtenerPorEncargado(Long encargadoId) {
        List<Bodega> bodegas = bodegaRepositorio.findByEncargadoId(encargadoId);
        return bodegas.stream().map(bodegaMapeador::entidadADTO).toList();
    }

    @Override
    public BodegaRespuesta actualizar(Long id, BodegaSolicitud dto) {
        Bodega bodega = bodegaRepositorio.findById(id).orElseThrow(() -> new RuntimeException("Bodega no encontrada con ese id"));
        Admin encargado = adminRepositorio.findById(dto.encargadoId()).orElseThrow(() -> new RuntimeException("Admin no encontrado con ese id"));
        bodegaMapeador.actualizarEntidadDesdeDTO(bodega, dto, encargado);
        return bodegaMapeador.entidadADTO(bodegaRepositorio.save(bodega));
    }

    @Override
    public void eliminar(Long id) {
        Bodega bodega = bodegaRepositorio.findById(id).orElseThrow(() -> new RuntimeException("Bodega no encontrado con ese id"));
        bodegaRepositorio.delete(bodega);
    }
}
