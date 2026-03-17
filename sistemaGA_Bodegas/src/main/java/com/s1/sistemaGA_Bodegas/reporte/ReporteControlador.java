package com.s1.sistemaGA_Bodegas.reporte;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reportes")
@RequiredArgsConstructor
public class ReporteControlador {
    private final ReporteServicio reporteServicio;

    @GetMapping("/resumen")
    public ResponseEntity<ResumenGeneralRespuesta> obtenerResumen() {
        return ResponseEntity.ok(reporteServicio.generarResumen());
    }
}
