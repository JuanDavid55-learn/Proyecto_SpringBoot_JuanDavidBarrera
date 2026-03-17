package com.s1.sistemaGA_Bodegas.repositorio;

import com.s1.sistemaGA_Bodegas.modelo.Bodega;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BodegaRepositorio extends JpaRepository<Bodega, Long>{

    List<Bodega> findByEncargadoId(Long encargadoId);

    boolean existsByNombre(String nombre);
}
