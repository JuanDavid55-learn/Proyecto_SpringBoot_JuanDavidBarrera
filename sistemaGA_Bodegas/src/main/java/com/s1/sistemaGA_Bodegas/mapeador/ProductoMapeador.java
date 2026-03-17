package com.s1.sistemaGA_Bodegas.mapeador;

import com.s1.sistemaGA_Bodegas.dto.solicitud.ProductoSolicitud;
import com.s1.sistemaGA_Bodegas.dto.respuesta.ProductoRespuesta;
import com.s1.sistemaGA_Bodegas.modelo.Producto;
import org.springframework.stereotype.Component;

@Component
public class ProductoMapeador {

    public ProductoRespuesta entidadADTO(Producto producto) {
        if (producto == null) return null;
        return new ProductoRespuesta(
                producto.getId(),
                producto.getNombre(),
                producto.getCategoria(),
                producto.getStock(),
                producto.getPrecio()
        );
    }

    public Producto DTOAEntidad(ProductoSolicitud dto) {
        if (dto == null) return null;
        Producto producto = new Producto();
        producto.setNombre(dto.nombre());
        producto.setCategoria(dto.categoria());
        producto.setStock(dto.stock());
        producto.setPrecio(dto.precio());
        return producto;
    }

    public void actualizarEntidadDesdeDTO(Producto producto, ProductoSolicitud dto) {
        if (producto == null || dto == null) return;
        producto.setNombre(dto.nombre());
        producto.setCategoria(dto.categoria());
        producto.setStock(dto.stock());
        producto.setPrecio(dto.precio());
    }
}
