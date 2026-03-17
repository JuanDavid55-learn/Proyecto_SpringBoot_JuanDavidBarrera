package com.s1.sistemaGA_Bodegas.mapeador;

import com.s1.sistemaGA_Bodegas.dto.solicitud.EmpleadoSolicitud;
import com.s1.sistemaGA_Bodegas.dto.respuesta.EmpleadoRespuesta;
import com.s1.sistemaGA_Bodegas.modelo.Empleado;
import org.springframework.stereotype.Component;

@Component
public class EmpleadoMapeador {

    public EmpleadoRespuesta entidadADTO(Empleado empleado) {
        if (empleado == null) return null;
        return new EmpleadoRespuesta(
                empleado.getId(),
                empleado.getNombre(),
                empleado.getDocumento(),
                empleado.getEdad(),
                empleado.getCorreo(),
                empleado.getUsuario()
        );
    }

    public Empleado DTOAEntidad(EmpleadoSolicitud dto) {
        if (dto == null) return null;
        Empleado empleado = new Empleado();
        empleado.setNombre(dto.nombre());
        empleado.setDocumento(dto.documento());
        empleado.setEdad(dto.edad());
        empleado.setCorreo(dto.correo());
        empleado.setUsuario(dto.usuario());
        return empleado;
    }

    public void actualizarEntidadDesdeDTO(Empleado empleado, EmpleadoSolicitud dto) {
        if (empleado == null || dto == null) return;
        empleado.setNombre(dto.nombre());
        empleado.setDocumento(dto.documento());
        empleado.setEdad(dto.edad());
        empleado.setCorreo(dto.correo());
        empleado.setUsuario(dto.usuario());
    }
}
