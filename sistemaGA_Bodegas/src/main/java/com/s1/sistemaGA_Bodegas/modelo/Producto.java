package com.s1.sistemaGA_Bodegas.modelo;

import com.s1.sistemaGA_Bodegas.listener.ProductoAuditoriaListener;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "producto")
@EntityListeners(ProductoAuditoriaListener.class)
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String categoria;

    @Min(0)
    @Column(nullable = false)
    private Long stock;

    @Column(nullable = false)
    private BigDecimal precio;
}
