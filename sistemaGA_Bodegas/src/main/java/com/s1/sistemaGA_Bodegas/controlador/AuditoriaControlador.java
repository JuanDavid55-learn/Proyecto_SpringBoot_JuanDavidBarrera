package com.s1.sistemaGA_Bodegas.controlador;

import com.s1.sistemaGA_Bodegas.dto.solicitud.AuditoriaSolicitud;
import com.s1.sistemaGA_Bodegas.dto.respuesta.AuditoriaRespuesta;
import com.s1.sistemaGA_Bodegas.modelo.TipoOperacion;
import com.s1.sistemaGA_Bodegas.servicio.AuditoriaServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/auditorias")
@RequiredArgsConstructor
@Validated
public class AuditoriaControlador {

    private final AuditoriaServicio auditoriaServicio;

    @PostMapping
    public ResponseEntity<AuditoriaRespuesta> registrar(@Valid @RequestBody AuditoriaSolicitud dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(auditoriaServicio.registrar(dto));
    }

    @GetMapping
    public ResponseEntity<List<AuditoriaRespuesta>> obtenerTodos() {
        return ResponseEntity.ok().body(auditoriaServicio.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuditoriaRespuesta> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok().body(auditoriaServicio.obtenerPorId(id));
    }

    @GetMapping("/tipo/{tipoOpc}")
    public ResponseEntity<List<AuditoriaRespuesta>> obtenerPorTipo(@PathVariable TipoOperacion tipoOpc) {
        return ResponseEntity.ok().body(auditoriaServicio.obtenerPorTipoOperacion(tipoOpc));
    }

    @GetMapping("/admin/{adminId}")
    public ResponseEntity<List<AuditoriaRespuesta>> obtenerPorAdmin(@PathVariable Long adminId) {
        return ResponseEntity.ok().body(auditoriaServicio.obtenerPorAdmin(adminId));
    }

    @GetMapping("/rango-fechas")
    public ResponseEntity<List<AuditoriaRespuesta>> obtenerPorRangoFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta) {
        return ResponseEntity.ok().body(auditoriaServicio.obtenerPorRangoFechas(desde, hasta));
    }
}
