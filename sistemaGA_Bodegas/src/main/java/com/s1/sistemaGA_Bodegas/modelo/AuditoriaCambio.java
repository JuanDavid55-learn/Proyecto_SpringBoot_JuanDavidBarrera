package com.s1.sistemaGA_Bodegas.modelo;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "auditoria_cambio")
public class AuditoriaCambio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auditoria_id", nullable = false)
    private Auditoria auditoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id")
    private Producto producto;

    @Column(nullable = false)
    private String campo;

    @Column(name = "categoria_anterior")
    private String categoriaAnterior;

    @Column(name = "categoria_nuevo")
    private String categoriaNuevo;

    @Column(name = "stock_anterior")
    private String stockAnterior;

    @Column(name = "stock_nuevo")
    private String stockNuevo;

    @Column(name = "precio_anterior")
    private String precioAnterior;

    @Column(name = "precio_nuevo")
    private String precioNuevo;
}
