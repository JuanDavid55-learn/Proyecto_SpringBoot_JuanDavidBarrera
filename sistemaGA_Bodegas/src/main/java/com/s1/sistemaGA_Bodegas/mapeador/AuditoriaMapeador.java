package com.s1.sistemaGA_Bodegas.mapeador;

import com.s1.sistemaGA_Bodegas.dto.solicitud.AuditoriaSolicitud;
import com.s1.sistemaGA_Bodegas.dto.respuesta.AuditoriaCambioRespuesta;
import com.s1.sistemaGA_Bodegas.dto.respuesta.AuditoriaRespuesta;
import com.s1.sistemaGA_Bodegas.modelo.Admin;
import com.s1.sistemaGA_Bodegas.modelo.Auditoria;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class AuditoriaMapeador {

    public AuditoriaRespuesta entidadADTO(Auditoria auditoria) {
        if (auditoria == null) return null;

        List<AuditoriaCambioRespuesta> cambios = auditoria.getCambios()
                .stream()
                .map(c -> new AuditoriaCambioRespuesta(
                        c.getId(),
                        c.getProducto() != null ? c.getProducto().getId()     : null,
                        c.getProducto() != null ? c.getProducto().getNombre() : null,
                        c.getCampo(),
                        c.getCategoriaAnterior(),
                        c.getCategoriaNuevo(),
                        c.getStockAnterior(),
                        c.getStockNuevo(),
                        c.getPrecioAnterior(),
                        c.getPrecioNuevo()
                ))
                .toList();

        return new AuditoriaRespuesta(
                auditoria.getId(),
                auditoria.getTipoOpc(),
                auditoria.getFechaHora(),
                auditoria.getResponsable().getId(),
                auditoria.getResponsable().getNombre(),
                cambios
        );
    }

    public Auditoria DTOAEntidad(AuditoriaSolicitud dto, Admin responsable) {
        if (dto == null || responsable == null) return null;
        Auditoria auditoria = new Auditoria();
        auditoria.setTipoOpc(dto.tipoOpc());
        auditoria.setFechaHora(LocalDateTime.now());
        auditoria.setResponsable(responsable);
        return auditoria;
    }
}
