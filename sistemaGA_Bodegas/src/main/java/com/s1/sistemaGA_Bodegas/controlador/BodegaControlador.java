package com.s1.sistemaGA_Bodegas.controlador;

import com.s1.sistemaGA_Bodegas.dto.solicitud.BodegaSolicitud;
import com.s1.sistemaGA_Bodegas.dto.respuesta.BodegaRespuesta;
import com.s1.sistemaGA_Bodegas.servicio.BodegaServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bodegas")
@RequiredArgsConstructor
@Validated
public class BodegaControlador {

    private final BodegaServicio bodegaServicio;

    @PostMapping
    public ResponseEntity<BodegaRespuesta> crear(@Valid @RequestBody BodegaSolicitud dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bodegaServicio.crear(dto));
    }

    @GetMapping
    public ResponseEntity<List<BodegaRespuesta>> obtenerTodos() {
        return ResponseEntity.ok().body(bodegaServicio.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BodegaRespuesta> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok().body(bodegaServicio.obtenerPorId(id));
    }

    @GetMapping("/encargado/{adminId}")
    public ResponseEntity<List<BodegaRespuesta>> obtenerPorEncargado(@PathVariable Long adminId) {
        return ResponseEntity.ok().body(bodegaServicio.obtenerPorEncargado(adminId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BodegaRespuesta> actualizar(@PathVariable Long id, @Valid @RequestBody BodegaSolicitud dto) {
        return ResponseEntity.ok().body(bodegaServicio.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        bodegaServicio.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
