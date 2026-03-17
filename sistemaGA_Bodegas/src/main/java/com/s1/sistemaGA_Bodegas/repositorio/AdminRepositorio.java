package com.s1.sistemaGA_Bodegas.repositorio;

import com.s1.sistemaGA_Bodegas.modelo.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AdminRepositorio extends JpaRepository<Admin, Long> {

    Optional<Admin> findByUsuario(String usuario);

    boolean existsByUsuario(String usuario);
}
