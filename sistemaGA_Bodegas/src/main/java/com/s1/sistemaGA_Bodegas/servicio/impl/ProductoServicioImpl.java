package com.s1.sistemaGA_Bodegas.servicio.impl;

import com.s1.sistemaGA_Bodegas.dto.solicitud.ProductoSolicitud;
import com.s1.sistemaGA_Bodegas.dto.respuesta.ProductoRespuesta;
import com.s1.sistemaGA_Bodegas.excepciones.BusinessRuleException;
import com.s1.sistemaGA_Bodegas.mapeador.ProductoMapeador;
import com.s1.sistemaGA_Bodegas.modelo.*;
import com.s1.sistemaGA_Bodegas.repositorio.*;
import com.s1.sistemaGA_Bodegas.servicio.ProductoServicio;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoServicioImpl implements ProductoServicio {

    private final ProductoRepositorio productoRepositorio;
    private final ProductoMapeador productoMapeador;
    private final MovimientoProductoRepositorio movimientoProductoRepositorio;
    private final AuditoriaCambioRepositorio auditoriaCambioRepositorio;
    private final AuditoriaRepositorio auditoriaRepositorio;
    private final AdminRepositorio adminRepositorio;

    @Override
    @Transactional
    public ProductoRespuesta crear(ProductoSolicitud dto) {
        Producto producto = productoMapeador.DTOAEntidad(dto);
        Producto guardado = productoRepositorio.save(producto);
        registrarAuditoria(guardado, TipoOperacion.INSERTAR, null, null, null);
        return productoMapeador.entidadADTO(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductoRespuesta obtenerPorId(Long id) {
        Producto producto = productoRepositorio.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado con ese id"));
        return productoMapeador.entidadADTO(producto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoRespuesta> obtenerTodos() {
        return productoRepositorio.findAll().stream()
                .map(productoMapeador::entidadADTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoRespuesta> obtenerPorCategoria(String categoria) {
        return productoRepositorio.findByCategoria(categoria).stream()
                .map(productoMapeador::entidadADTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoRespuesta> obtenerConStockBajoMinimo(Long stock) {
        return productoRepositorio.findByStockLessThan(stock).stream()
                .map(productoMapeador::entidadADTO).toList();
    }

    @Override
    @Transactional
    public ProductoRespuesta actualizar(Long id, ProductoSolicitud dto) {
        Producto producto = productoRepositorio.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado con ese id"));

        // Guarda valores anteriores ANTES de modificar
        String catAnterior    = producto.getCategoria();
        String stockAnterior  = String.valueOf(producto.getStock());
        String precioAnterior = String.valueOf(producto.getPrecio());

        productoMapeador.actualizarEntidadDesdeDTO(producto, dto);
        Producto actualizado = productoRepositorio.save(producto);

        // Registra auditoría con anterior y nuevo
        registrarAuditoria(actualizado, TipoOperacion.ACTUALIZAR,
                catAnterior, stockAnterior, precioAnterior);
        return productoMapeador.entidadADTO(actualizado);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Producto producto = productoRepositorio.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado con ese id"));

        if (!movimientoProductoRepositorio.findByProductoId(id).isEmpty()) {
            throw new BusinessRuleException(
                    "No se puede eliminar el producto '" + producto.getNombre() +
                            "' porque tiene movimientos de inventario registrados.");
        }
        if (!auditoriaCambioRepositorio.findByProductoId(id).isEmpty()) {
            throw new BusinessRuleException(
                    "No se puede eliminar el producto '" + producto.getNombre() +
                            "' porque tiene auditorias registradas. " +
                            "Puede actualizar su stock a 0 en su lugar.");
        }

        // Auditoría ANTES de eliminar — después ya no existirá
        registrarAuditoria(producto, TipoOperacion.ELIMINAR, null, null, null);
        productoRepositorio.delete(producto);
    }

    // Auditoría directa en el servicio (sin EntityListener)

    private void registrarAuditoria(Producto producto, TipoOperacion tipo,
                                    String catAnterior, String stockAnterior, String precioAnterior) {
        try {
            Auditoria auditoria = new Auditoria();
            auditoria.setTipoOpc(tipo);
            auditoria.setFechaHora(LocalDateTime.now());
            auditoria.setResponsable(getAdminAutenticado());

            AuditoriaCambio cambio = new AuditoriaCambio();
            cambio.setAuditoria(auditoria);
            // Para ELIMINAR no referenciamos el producto (la FK quedaría rota)
            if (tipo != TipoOperacion.ELIMINAR) cambio.setProducto(producto);
            cambio.setCampo("producto_completo");
            cambio.setCategoriaAnterior(catAnterior);
            cambio.setCategoriaNuevo(producto.getCategoria());
            cambio.setStockAnterior(stockAnterior);
            cambio.setStockNuevo(String.valueOf(producto.getStock()));
            cambio.setPrecioAnterior(precioAnterior);
            cambio.setPrecioNuevo(String.valueOf(producto.getPrecio()));

            auditoria.getCambios().add(cambio);
            auditoriaRepositorio.save(auditoria);

        } catch (Exception e) {
            System.err.println("[ProductoServicio] Error al registrar auditoria: " + e.getMessage());
        }
    }

    private Admin getAdminAutenticado() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.isAuthenticated()
                    && !auth.getPrincipal().equals("anonymousUser")) {
                return adminRepositorio.findByUsuario(auth.getName()).orElse(null);
            }
        } catch (Exception ignored) {}
        return null;
    }
}