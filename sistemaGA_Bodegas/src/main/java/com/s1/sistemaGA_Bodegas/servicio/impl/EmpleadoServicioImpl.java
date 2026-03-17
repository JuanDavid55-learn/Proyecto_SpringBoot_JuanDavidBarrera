package com.s1.sistemaGA_Bodegas.servicio.impl;

import com.s1.sistemaGA_Bodegas.dto.solicitud.EmpleadoSolicitud;
import com.s1.sistemaGA_Bodegas.dto.respuesta.EmpleadoRespuesta;
import com.s1.sistemaGA_Bodegas.excepciones.BusinessRuleException;
import com.s1.sistemaGA_Bodegas.mapeador.EmpleadoMapeador;
import com.s1.sistemaGA_Bodegas.modelo.Empleado;
import com.s1.sistemaGA_Bodegas.repositorio.EmpleadoRepositorio;
import com.s1.sistemaGA_Bodegas.repositorio.MovimientoRepositorio;
import com.s1.sistemaGA_Bodegas.servicio.EmpleadoServicio;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmpleadoServicioImpl implements EmpleadoServicio {

    private final EmpleadoRepositorio empleadoRepositorio;
    private final EmpleadoMapeador empleadoMapeador;
    private final PasswordEncoder passwordEncoder;
    private final MovimientoRepositorio movimientoRepositorio;

    @Override
    @Transactional
    public EmpleadoRespuesta crear(EmpleadoSolicitud dto) {
        if (empleadoRepositorio.existsByUsuario(dto.usuario())) {
            throw new BusinessRuleException("El usuario empleado ya existe: " + dto.usuario());
        }
        if (dto.contrasena() == null || dto.contrasena().isBlank()) {
            throw new BusinessRuleException("La contraseña es obligatoria al crear un empleado.");
        }
        Empleado empleado = empleadoMapeador.DTOAEntidad(dto);
        empleado.setContrasena(passwordEncoder.encode(dto.contrasena()));
        return empleadoMapeador.entidadADTO(empleadoRepositorio.save(empleado));
    }

    @Override
    @Transactional(readOnly = true)
    public EmpleadoRespuesta obtenerPorId(Long id) {
        Empleado empleado = empleadoRepositorio.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado con ese id"));
        return empleadoMapeador.entidadADTO(empleado);
    }

    @Override
    @Transactional(readOnly = true)
    public EmpleadoRespuesta obtenerPorUsuario(String usuario) {
        Empleado empleado = empleadoRepositorio.findByUsuario(usuario)
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado con ese usuario"));
        return empleadoMapeador.entidadADTO(empleado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmpleadoRespuesta> obtenerTodos() {
        return empleadoRepositorio.findAll().stream()
                .map(empleadoMapeador::entidadADTO).toList();
    }

    @Override
    @Transactional
    public EmpleadoRespuesta actualizar(Long id, EmpleadoSolicitud dto) {
        Empleado empleado = empleadoRepositorio.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado con ese id"));
        empleadoMapeador.actualizarEntidadDesdeDTO(empleado, dto);
        // Solo actualiza la contraseña si viene con valor en el request
        if (dto.contrasena() != null && !dto.contrasena().isBlank()) {
            empleado.setContrasena(passwordEncoder.encode(dto.contrasena()));
        }
        return empleadoMapeador.entidadADTO(empleadoRepositorio.save(empleado));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Empleado empleado = empleadoRepositorio.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado con ese id"));
        if (!movimientoRepositorio.findByResponsableId(id).isEmpty()) {
            throw new BusinessRuleException(
                    "No se puede eliminar el empleado '" + empleado.getUsuario() +
                            "' porque tiene movimientos de inventario registrados a su nombre.");
        }
        empleadoRepositorio.delete(empleado);
    }
}
