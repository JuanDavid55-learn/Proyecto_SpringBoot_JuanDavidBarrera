package com.s1.sistemaGA_Bodegas.controlador;

import com.s1.sistemaGA_Bodegas.dto.solicitud.ProductoSolicitud;
import com.s1.sistemaGA_Bodegas.dto.respuesta.ProductoRespuesta;
import com.s1.sistemaGA_Bodegas.servicio.ProductoServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
@Validated
public class ProductoControlador {

    private final ProductoServicio productoServicio;

    @PostMapping
    public ResponseEntity<ProductoRespuesta> crear(@Valid @RequestBody ProductoSolicitud dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productoServicio.crear(dto));
    }

    @GetMapping
    public ResponseEntity<List<ProductoRespuesta>> obtenerTodos() {
        return ResponseEntity.ok().body(productoServicio.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoRespuesta> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok().body(productoServicio.obtenerPorId(id));
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<ProductoRespuesta>> obtenerPorCategoria(@PathVariable String categoria) {
        return ResponseEntity.ok().body(productoServicio.obtenerPorCategoria(categoria));
    }

    @GetMapping("/stock-bajo")
    public ResponseEntity<List<ProductoRespuesta>> obtenerConStockBajo(@RequestParam Long minimo) {
        return ResponseEntity.ok().body(productoServicio.obtenerConStockBajoMinimo(minimo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoRespuesta> actualizar(@PathVariable Long id, @Valid @RequestBody ProductoSolicitud dto) {
        return ResponseEntity.ok().body(productoServicio.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoServicio.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
