package com.s1.sistemaGA_Bodegas.repositorio;

import com.s1.sistemaGA_Bodegas.modelo.MovimientoProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MovimientoProductoRepositorio extends JpaRepository<MovimientoProducto, Long>{

    List<MovimientoProducto> findByMovimientoId(Long movimientoId);

    List<MovimientoProducto> findByProductoId(Long productoId);

    /*
     * Devuelve el total de unidades movidas por cada producto,
     * ordenado de mayor a menor — para el top de productos más movidos.
     */
    @Query("""
            SELECT mp.producto.id, mp.producto.nombre, mp.producto.categoria,
                   mp.producto.stock, SUM(mp.cantidad)
            FROM MovimientoProducto mp
            GROUP BY mp.producto.id, mp.producto.nombre,
                     mp.producto.categoria, mp.producto.stock
            ORDER BY SUM(mp.cantidad) DESC
            """) List<Object[]> findProductosMasMovidos();

    /*
     * Devuelve el total de movimientos y unidades movidas por bodega origen.
     */
    @Query("""
            SELECT m.bodegaOrigen.id, m.bodegaOrigen.nombre, m.bodegaOrigen.ubicacion,
                   COUNT(m.id), SUM(mp.cantidad)
            FROM MovimientoProducto mp
            JOIN mp.movimiento m
            WHERE m.bodegaOrigen IS NOT NULL
            GROUP BY m.bodegaOrigen.id, m.bodegaOrigen.nombre, m.bodegaOrigen.ubicacion
            """) List<Object[]> findStockMovidoPorBodegaOrigen();

    /*
     * Devuelve el total de movimientos y unidades movidas por bodega destino.
     */
    @Query("""
            SELECT m.bodegaDestino.id, m.bodegaDestino.nombre, m.bodegaDestino.ubicacion,
                   COUNT(m.id), SUM(mp.cantidad)
            FROM MovimientoProducto mp
            JOIN mp.movimiento m
            WHERE m.bodegaDestino IS NOT NULL
            GROUP BY m.bodegaDestino.id, m.bodegaDestino.nombre, m.bodegaDestino.ubicacion
            """) List<Object[]> findStockMovidoPorBodegaDestino();
}
