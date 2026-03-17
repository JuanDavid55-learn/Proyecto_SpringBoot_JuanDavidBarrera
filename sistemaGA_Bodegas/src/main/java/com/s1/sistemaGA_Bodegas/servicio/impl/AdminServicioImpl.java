package com.s1.sistemaGA_Bodegas.servicio.impl;

import com.s1.sistemaGA_Bodegas.dto.solicitud.AdminSolicitud;
import com.s1.sistemaGA_Bodegas.dto.respuesta.AdminRespuesta;
import com.s1.sistemaGA_Bodegas.mapeador.AdminMapeador;
import com.s1.sistemaGA_Bodegas.modelo.Admin;
import com.s1.sistemaGA_Bodegas.repositorio.AdminRepositorio;
import com.s1.sistemaGA_Bodegas.servicio.AdminServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServicioImpl implements AdminServicio{
    private final AdminRepositorio adminRepositorio;
    private final AdminMapeador adminMapeador;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AdminRespuesta crear(AdminSolicitud dto){
        if (adminRepositorio.existsByUsuario(dto.usuario())) {
            throw new RuntimeException("El usuario administrador ya existe: ");
        }
        Admin admin = adminMapeador.DTOAEntidad(dto);
        admin.setContrasena(passwordEncoder.encode(dto.contrasena()));
        return adminMapeador.entidadADTO(adminRepositorio.save(admin));
    }

    @Override
    public AdminRespuesta obtenerPorId(Long id) {
        Admin admin = adminRepositorio.findById(id).orElseThrow(() -> new RuntimeException("Admin no encontrado con ese id"));
        return adminMapeador.entidadADTO(admin);
    }

    @Override
    public AdminRespuesta obtenerPorUsuario(String usuario) {
        Admin admin = adminRepositorio.findByUsuario(usuario).orElseThrow(() -> new RuntimeException("Admin no encontrado con ese usuario"));
        return adminMapeador.entidadADTO(admin);
    }

    @Override
    public List<AdminRespuesta> obtenerTodos() {
        List<Admin> admins= adminRepositorio.findAll();
        return admins.stream().map(adminMapeador::entidadADTO).toList();
    }

    @Override
    public AdminRespuesta actualizar(Long id, AdminSolicitud dto) {
        Admin admin = adminRepositorio.findById(id).orElseThrow(() -> new RuntimeException("Admin no encontrado con ese id"));
        adminMapeador.actualizarEntidadDesdeDTO(admin, dto);
        admin.setContrasena(passwordEncoder.encode(dto.contrasena()));
        return adminMapeador.entidadADTO(adminRepositorio.save(admin));
    }

    @Override
    public void eliminar(Long id) {
        Admin admin = adminRepositorio.findById(id).orElseThrow(() -> new RuntimeException("Admin no encontrado con ese id"));
        adminRepositorio.delete(admin);
    }
}
