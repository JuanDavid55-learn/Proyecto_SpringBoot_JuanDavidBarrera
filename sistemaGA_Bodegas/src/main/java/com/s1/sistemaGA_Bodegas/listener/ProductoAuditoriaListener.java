package com.s1.sistemaGA_Bodegas.listener;

import com.s1.sistemaGA_Bodegas.modelo.*;
import com.s1.sistemaGA_Bodegas.repositorio.AdminRepositorio;
import com.s1.sistemaGA_Bodegas.repositorio.AuditoriaRepositorio;
import jakarta.persistence.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;

public class ProductoAuditoriaListener {

    @PostPersist
    public void despuesDeInsertar(Producto producto) {
        registrarAuditoria(producto, TipoOperacion.INSERTAR);
    }

    @PostUpdate
    public void despuesDeActualizar(Producto producto) {
        registrarAuditoria(producto, TipoOperacion.ACTUALIZAR);
    }

    @PreRemove
    public void antesDeEliminar(Producto producto) {
        registrarAuditoria(producto, TipoOperacion.ELIMINAR);
    }

    private void registrarAuditoria(Producto producto, TipoOperacion tipoOpc) {
        try {
            AuditoriaRepositorio auditoriaRepositorio =
                    SpringContext.getBean(AuditoriaRepositorio.class);
            AdminRepositorio adminRepositorio =
                    SpringContext.getBean(AdminRepositorio.class);

            Admin responsable = null;
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.isAuthenticated()) {
                responsable = adminRepositorio.findByUsuario(auth.getName()).orElse(null);
            }

            Auditoria auditoria = new Auditoria();
            auditoria.setTipoOpc(tipoOpc);
            auditoria.setFechaHora(LocalDateTime.now());
            auditoria.setResponsable(responsable);

            AuditoriaCambio cambio = new AuditoriaCambio();
            cambio.setAuditoria(auditoria);
            cambio.setProducto(tipoOpc == TipoOperacion.ELIMINAR ? null : producto);
            cambio.setCampo("producto_completo");
            cambio.setCategoriaNuevo(producto.getCategoria());
            cambio.setStockNuevo(String.valueOf(producto.getStock()));
            cambio.setPrecioNuevo(String.valueOf(producto.getPrecio()));

            auditoria.getCambios().add(cambio);
            auditoriaRepositorio.save(auditoria);

        } catch (Exception e) {
            System.err.println("Error al registrar auditoría automática: " + e.getMessage());
        }
    }
}
