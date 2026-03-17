package com.s1.sistemaGA_Bodegas.controlador;

import com.s1.sistemaGA_Bodegas.dto.solicitud.AdminSolicitud;
import com.s1.sistemaGA_Bodegas.dto.respuesta.AdminRespuesta;
import com.s1.sistemaGA_Bodegas.servicio.AdminServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admins")
@RequiredArgsConstructor
@Validated
public class AdminControlador {

    private final AdminServicio adminServicio;

    @PostMapping
    public ResponseEntity<AdminRespuesta> crear(@Valid @RequestBody AdminSolicitud dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminServicio.crear(dto));
    }

    @GetMapping
    public ResponseEntity<List<AdminRespuesta>> obtenerTodos() {
        return ResponseEntity.ok().body(adminServicio.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminRespuesta> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok().body(adminServicio.obtenerPorId(id));
    }

    @GetMapping("/usuario/{usuario}")
    public ResponseEntity<AdminRespuesta> obtenerPorUsuario(@PathVariable String usuario) {
        return ResponseEntity.ok().body(adminServicio.obtenerPorUsuario(usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdminRespuesta> actualizar(@PathVariable Long id, @Valid @RequestBody AdminSolicitud dto) {
        return ResponseEntity.ok().body(adminServicio.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        adminServicio.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
