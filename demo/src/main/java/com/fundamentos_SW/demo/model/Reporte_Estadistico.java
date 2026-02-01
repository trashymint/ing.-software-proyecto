package com.fundamentos_SW.demo.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Reporte_Estadistico")
public class Reporte_Estadistico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idReporteEstadistico;

    @Column(name = "Fecha_Inicio")
    private LocalDateTime fechaInicio;

    @Column(name = "Fecha_Fin")
    private LocalDateTime fechaFin;

    @Column(name = "Productos_Vendidos")
    private Integer productosVendidos;

    private Double ganancias;

    @ManyToOne
    @JoinColumn(name = "idProducto")
    private Producto producto;

    // Getters y Setters

    public int getIdReporteEstadistico() {
        return idReporteEstadistico;
    }

    public void setIdReporteEstadistico(int idReporteEstadistico) {
        this.idReporteEstadistico = idReporteEstadistico;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Integer getProductosVendidos() {
        return productosVendidos;
    }

    public void setProductosVendidos(Integer productosVendidos) {
        this.productosVendidos = productosVendidos;
    }

    public Double getGanancias() {
        return ganancias;
    }

    public void setGanancias(Double ganancias) {
        this.ganancias = ganancias;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
}

