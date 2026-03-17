package com.s1.sistemaGA_Bodegas.mapeador;

import com.s1.sistemaGA_Bodegas.dto.solicitud.BodegaSolicitud;
import com.s1.sistemaGA_Bodegas.dto.respuesta.BodegaRespuesta;
import com.s1.sistemaGA_Bodegas.modelo.Admin;
import com.s1.sistemaGA_Bodegas.modelo.Bodega;
import org.springframework.stereotype.Component;

@Component
public class BodegaMapeador {

    public BodegaRespuesta entidadADTO(Bodega bodega) {
        if (bodega == null) return null;
        return new BodegaRespuesta(
                bodega.getId(),
                bodega.getNombre(),
                bodega.getUbicacion(),
                bodega.getCapacidad(),
                bodega.getEncargado().getId(),
                bodega.getEncargado().getNombre()
        );
    }

    public Bodega DTOAEntidad(BodegaSolicitud dto, Admin encargado) {
        if (dto == null || encargado == null) return null;
        Bodega bodega = new Bodega();
        bodega.setNombre(dto.nombre());
        bodega.setUbicacion(dto.ubicacion());
        bodega.setCapacidad(dto.capacidad());
        bodega.setEncargado(encargado);
        return bodega;
    }

    public void actualizarEntidadDesdeDTO(Bodega bodega, BodegaSolicitud dto, Admin encargado) {
        if (bodega == null || dto == null || encargado == null) return;
        bodega.setNombre(dto.nombre());
        bodega.setUbicacion(dto.ubicacion());
        bodega.setCapacidad(dto.capacidad());
        bodega.setEncargado(encargado);
    }
}
