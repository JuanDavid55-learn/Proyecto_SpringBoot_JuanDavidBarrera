package com.s1.sistemaGA_Bodegas.servicio.impl;

import com.s1.sistemaGA_Bodegas.dto.solicitud.ProductoSolicitud;
import com.s1.sistemaGA_Bodegas.dto.respuesta.ProductoRespuesta;
import com.s1.sistemaGA_Bodegas.mapeador.ProductoMapeador;
import com.s1.sistemaGA_Bodegas.modelo.Producto;
import com.s1.sistemaGA_Bodegas.repositorio.ProductoRepositorio;
import com.s1.sistemaGA_Bodegas.servicio.ProductoServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoServicioImpl implements ProductoServicio{

    private final ProductoRepositorio productoRepositorio;
    private final ProductoMapeador productoMapeador;

    @Override
    public ProductoRespuesta crear(ProductoSolicitud dto) {
        Producto producto = productoMapeador.DTOAEntidad(dto);
        return productoMapeador.entidadADTO(productoRepositorio.save(producto));
    }

    @Override
    public ProductoRespuesta obtenerPorId(Long id) {
        Producto producto = productoRepositorio.findById(id).orElseThrow(() -> new RuntimeException("Producto no encontrado con ese id"));
        return productoMapeador.entidadADTO(producto);
    }

    @Override
    public List<ProductoRespuesta> obtenerTodos() {
        List<Producto> productos = productoRepositorio.findAll();
        return productos.stream().map(productoMapeador::entidadADTO).toList();
    }

    @Override
    public List<ProductoRespuesta> obtenerPorCategoria(String categoria) {
        List<Producto> productos = productoRepositorio.findByCategoria(categoria);
        return productos.stream().map(productoMapeador::entidadADTO).toList();
    }

    @Override
    public List<ProductoRespuesta> obtenerConStockBajoMinimo(Long stock) {
        List<Producto> productos = productoRepositorio.findByStockLessThan(stock);
        return productos.stream().map(productoMapeador::entidadADTO).toList();
    }

    @Override
    public ProductoRespuesta actualizar(Long id, ProductoSolicitud dto) {
        Producto producto = productoRepositorio.findById(id).orElseThrow(() -> new RuntimeException("Producto no encontrado con ese id"));
        productoMapeador.actualizarEntidadDesdeDTO(producto, dto);
        return productoMapeador.entidadADTO(productoRepositorio.save(producto));
    }

    @Override
    public void eliminar(Long id) {
        Producto producto = productoRepositorio.findById(id).orElseThrow(() -> new RuntimeException("Producto no encontrado con ese id"));
        productoRepositorio.delete(producto);
    }
}
