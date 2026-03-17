package com.s1.sistemaGA_Bodegas.repositorio;

import com.s1.sistemaGA_Bodegas.modelo.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductoRepositorio extends JpaRepository<Producto, Long>{

    List<Producto> findByCategoria(String categoria);

    List<Producto> findByStockLessThan(Long stock);

    boolean existsByNombre(String nombre);
}
