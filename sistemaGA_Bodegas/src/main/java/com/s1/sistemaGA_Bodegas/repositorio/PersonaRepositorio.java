package com.s1.sistemaGA_Bodegas.repositorio;

import com.s1.sistemaGA_Bodegas.modelo.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PersonaRepositorio extends JpaRepository <Persona, Long>{

    List<Persona> findByDocumento(String documento);

    List<Persona> findByCorreo(String correo);

    boolean existsByDocumento(String documento);

    boolean existsByCorreo(String correo);
}
