package com.s1.sistemaGA_Bodegas.mapeador;

import com.s1.sistemaGA_Bodegas.dto.solicitud.AdminSolicitud;
import com.s1.sistemaGA_Bodegas.dto.respuesta.AdminRespuesta;
import com.s1.sistemaGA_Bodegas.modelo.Admin;
import org.springframework.stereotype.Component;

@Component
public class AdminMapeador {

    public AdminRespuesta entidadADTO(Admin admin) {
        if (admin == null) return null;
        return new AdminRespuesta(
                admin.getId(),
                admin.getNombre(),
                admin.getDocumento(),
                admin.getEdad(),
                admin.getCorreo(),
                admin.getUsuario()
        );
    }

    public Admin DTOAEntidad(AdminSolicitud dto) {
        if (dto == null) return null;
        Admin admin = new Admin();
        admin.setNombre(dto.nombre());
        admin.setDocumento(dto.documento());
        admin.setEdad(dto.edad());
        admin.setCorreo(dto.correo());
        admin.setUsuario(dto.usuario());
        return admin;
    }

    public void actualizarEntidadDesdeDTO(Admin admin, AdminSolicitud dto) {
        if (admin == null || dto == null) return;
        admin.setNombre(dto.nombre());
        admin.setDocumento(dto.documento());
        admin.setEdad(dto.edad());
        admin.setCorreo(dto.correo());
        admin.setUsuario(dto.usuario());
    }
}
