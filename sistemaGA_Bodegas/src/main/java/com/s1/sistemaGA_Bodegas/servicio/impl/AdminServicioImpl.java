package com.s1.sistemaGA_Bodegas.servicio.impl;

import com.s1.sistemaGA_Bodegas.dto.solicitud.AdminSolicitud;
import com.s1.sistemaGA_Bodegas.dto.respuesta.AdminRespuesta;
import com.s1.sistemaGA_Bodegas.excepciones.BusinessRuleException;
import com.s1.sistemaGA_Bodegas.mapeador.AdminMapeador;
import com.s1.sistemaGA_Bodegas.modelo.Admin;
import com.s1.sistemaGA_Bodegas.repositorio.AdminRepositorio;
import com.s1.sistemaGA_Bodegas.repositorio.AuditoriaRepositorio;
import com.s1.sistemaGA_Bodegas.repositorio.BodegaRepositorio;
import com.s1.sistemaGA_Bodegas.servicio.AdminServicio;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServicioImpl implements AdminServicio {

    private final AdminRepositorio adminRepositorio;
    private final AdminMapeador adminMapeador;
    private final PasswordEncoder passwordEncoder;
    private final AuditoriaRepositorio auditoriaRepositorio;
    private final BodegaRepositorio bodegaRepositorio;

    @Override
    @Transactional
    public AdminRespuesta crear(AdminSolicitud dto) {
        if (adminRepositorio.existsByUsuario(dto.usuario())) {
            throw new BusinessRuleException("El usuario administrador ya existe: " + dto.usuario());
        }
        if (dto.contrasena() == null || dto.contrasena().isBlank()) {
            throw new BusinessRuleException("La contraseña es obligatoria al crear un admin.");
        }
        Admin admin = adminMapeador.DTOAEntidad(dto);
        admin.setContrasena(passwordEncoder.encode(dto.contrasena()));
        return adminMapeador.entidadADTO(adminRepositorio.save(admin));
    }

    @Override
    @Transactional(readOnly = true)
    public AdminRespuesta obtenerPorId(Long id) {
        Admin admin = adminRepositorio.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Admin no encontrado con ese id"));
        return adminMapeador.entidadADTO(admin);
    }

    @Override
    @Transactional(readOnly = true)
    public AdminRespuesta obtenerPorUsuario(String usuario) {
        Admin admin = adminRepositorio.findByUsuario(usuario)
                .orElseThrow(() -> new EntityNotFoundException("Admin no encontrado con ese usuario"));
        return adminMapeador.entidadADTO(admin);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdminRespuesta> obtenerTodos() {
        return adminRepositorio.findAll().stream()
                .map(adminMapeador::entidadADTO).toList();
    }

    @Override
    @Transactional
    public AdminRespuesta actualizar(Long id, AdminSolicitud dto) {
        Admin admin = adminRepositorio.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Admin no encontrado con ese id"));
        adminMapeador.actualizarEntidadDesdeDTO(admin, dto);
        // Solo actualiza la contraseña si viene con valor en el request
        if (dto.contrasena() != null && !dto.contrasena().isBlank()) {
            admin.setContrasena(passwordEncoder.encode(dto.contrasena()));
        }
        return adminMapeador.entidadADTO(adminRepositorio.save(admin));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Admin admin = adminRepositorio.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Admin no encontrado con ese id"));
        if (!bodegaRepositorio.findByEncargadoId(id).isEmpty()) {
            throw new BusinessRuleException(
                    "No se puede eliminar el admin '" + admin.getUsuario() +
                            "' porque tiene bodegas asignadas. Reasigna las bodegas primero.");
        }
        if (!auditoriaRepositorio.findByResponsableId(id).isEmpty()) {
            throw new BusinessRuleException(
                    "No se puede eliminar el admin '" + admin.getUsuario() +
                            "' porque tiene auditorias registradas a su nombre.");
        }
        adminRepositorio.delete(admin);
    }
}
