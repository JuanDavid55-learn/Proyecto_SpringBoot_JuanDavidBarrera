package com.s1.sistemaGA_Bodegas.controlador;

import com.s1.sistemaGA_Bodegas.dto.solicitud.MovimientoSolicitud;
import com.s1.sistemaGA_Bodegas.dto.respuesta.MovimientoRespuesta;
import com.s1.sistemaGA_Bodegas.modelo.TipoMovimiento;
import com.s1.sistemaGA_Bodegas.servicio.MovimientoServicio;
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
@RequestMapping("/api/movimientos")
@RequiredArgsConstructor
@Validated
public class MovimientoControlador {

    private final MovimientoServicio movimientoServicio;

    @PostMapping
    public ResponseEntity<MovimientoRespuesta> registrar(@Valid @RequestBody MovimientoSolicitud dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(movimientoServicio.registrar(dto));
    }

    @GetMapping
    public ResponseEntity<List<MovimientoRespuesta>> obtenerTodos() {
        return ResponseEntity.ok().body(movimientoServicio.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovimientoRespuesta> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok().body(movimientoServicio.obtenerPorId(id));
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<MovimientoRespuesta>> obtenerPorTipo(@PathVariable TipoMovimiento tipo) {
        return ResponseEntity.ok().body(movimientoServicio.obtenerPorTipo(tipo));
    }

    @GetMapping("/empleado/{empleadoId}")
    public ResponseEntity<List<MovimientoRespuesta>> obtenerPorEmpleado(@PathVariable Long empleadoId) {
        return ResponseEntity.ok().body(movimientoServicio.obtenerPorEmpleado(empleadoId));
    }

    @GetMapping("/bodega-origen/{bodegaId}")
    public ResponseEntity<List<MovimientoRespuesta>> obtenerPorBodegaOrigen(@PathVariable Long bodegaId) {
        return ResponseEntity.ok().body(movimientoServicio.obtenerPorBodegaOrigen(bodegaId));
    }

    @GetMapping("/bodega-destino/{bodegaId}")
    public ResponseEntity<List<MovimientoRespuesta>> obtenerPorBodegaDestino(@PathVariable Long bodegaId) {
        return ResponseEntity.ok().body(movimientoServicio.obtenerPorBodegaDestino(bodegaId));
    }

    @GetMapping("/rango-fechas")
    public ResponseEntity<List<MovimientoRespuesta>> obtenerPorRangoFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta) {
        return ResponseEntity.ok().body(movimientoServicio.obtenerPorRangoFechas(desde, hasta));
    }

    @GetMapping("/recientes")
    public ResponseEntity<List<MovimientoRespuesta>> listarRecientes() {
        return ResponseEntity.ok().body(movimientoServicio.obtenerRecientes());
    }
}
