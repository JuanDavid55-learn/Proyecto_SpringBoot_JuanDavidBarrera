package com.s1.sistemaGA_Bodegas.servicio.impl;

import com.s1.sistemaGA_Bodegas.dto.solicitud.EmpleadoSolicitud;
import com.s1.sistemaGA_Bodegas.dto.respuesta.EmpleadoRespuesta;
import com.s1.sistemaGA_Bodegas.mapeador.EmpleadoMapeador;
import com.s1.sistemaGA_Bodegas.modelo.Empleado;
import com.s1.sistemaGA_Bodegas.repositorio.EmpleadoRepositorio;
import com.s1.sistemaGA_Bodegas.servicio.EmpleadoServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmpleadoServicioImpl implements EmpleadoServicio {

    private final EmpleadoRepositorio empleadoRepositorio;
    private final EmpleadoMapeador empleadoMapeador;
    private final PasswordEncoder passwordEncoder;

    @Override
    public EmpleadoRespuesta crear(EmpleadoSolicitud dto) {
        if (empleadoRepositorio.existsByUsuario(dto.usuario())) {
            throw new RuntimeException("El usuario empleado ya existe: ");
        }
        Empleado emple = empleadoMapeador.DTOAEntidad(dto);
        emple.setContrasena(passwordEncoder.encode(dto.contrasena()));
        return empleadoMapeador.entidadADTO(empleadoRepositorio.save(emple));
    }

    @Override
    public EmpleadoRespuesta obtenerPorId(Long id) {
        Empleado emple = empleadoRepositorio.findById(id).orElseThrow(() -> new RuntimeException("Empleado no encontrado con ese id"));
        return empleadoMapeador.entidadADTO(emple);
    }

    @Override
    public EmpleadoRespuesta obtenerPorUsuario(String usuario) {
        Empleado emple = empleadoRepositorio.findByUsuario(usuario).orElseThrow(() -> new RuntimeException("Empleado no encontrado con ese usuario"));
        return empleadoMapeador.entidadADTO(emple);
    }

    @Override
    public List<EmpleadoRespuesta> obtenerTodos() {
        List<Empleado> empleados = empleadoRepositorio.findAll();
        return empleados.stream().map(empleadoMapeador::entidadADTO).toList();
    }

    @Override
    public EmpleadoRespuesta actualizar(Long id, EmpleadoSolicitud dto) {
        Empleado emple = empleadoRepositorio.findById(id).orElseThrow(() -> new RuntimeException("Empleado no encontrado con ese id"));
        empleadoMapeador.actualizarEntidadDesdeDTO(emple, dto);
        emple.setContrasena(passwordEncoder.encode(dto.contrasena()));
        return empleadoMapeador.entidadADTO(empleadoRepositorio.save(emple));
    }

    @Override
    public void eliminar(Long id) {
        Empleado emple = empleadoRepositorio.findById(id).orElseThrow(() -> new RuntimeException("Empleado no encontrado con ese id"));
        empleadoRepositorio.delete(emple);
    }
}
