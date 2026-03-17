package com.s1.sistemaGA_Bodegas.controlador;

import com.s1.sistemaGA_Bodegas.dto.solicitud.EmpleadoSolicitud;
import com.s1.sistemaGA_Bodegas.dto.respuesta.EmpleadoRespuesta;
import com.s1.sistemaGA_Bodegas.servicio.EmpleadoServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/empleados")
@RequiredArgsConstructor
@Validated
public class EmpleadoControlador {

    private final EmpleadoServicio empleadoServicio;

    @PostMapping
    public ResponseEntity<EmpleadoRespuesta> crear(@Valid @RequestBody EmpleadoSolicitud dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(empleadoServicio.crear(dto));
    }

    @GetMapping
    public ResponseEntity<List<EmpleadoRespuesta>> obtenerTodos() {
        return ResponseEntity.ok().body(empleadoServicio.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoRespuesta> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok().body(empleadoServicio.obtenerPorId(id));
    }

    @GetMapping("/usuario/{usuario}")
    public ResponseEntity<EmpleadoRespuesta> obtenerPorUsuario(@PathVariable String usuario) {
        return ResponseEntity.ok().body(empleadoServicio.obtenerPorUsuario(usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpleadoRespuesta> actualizar(@PathVariable Long id, @Valid @RequestBody EmpleadoSolicitud dto) {
        return ResponseEntity.ok().body(empleadoServicio.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        empleadoServicio.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
