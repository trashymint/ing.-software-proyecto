package com.fundamentos_SW.demo.model;

import jakarta.persistence.*;
import java.util.*;
@Entity
@Table(name = "Reporte_Fraude")
public class Reporte_Fraude {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idReporte_Fraude")
    private int idReporteFraude;

    private String descripcion;
    private String tipo;
    private boolean estado;

    @Column(name = "ResolucionComentarios")
    private String resolucionComentarios;

    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "idProducto")
    private Producto producto;

    // Getters y setters

    public int getIdReporteFraude() {
        return idReporteFraude;
    }

    public void setIdReporteFraude(int idReporteFraude) {
        this.idReporteFraude = idReporteFraude;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getResolucionComentarios() {
        return resolucionComentarios;
    }

    public void setResolucionComentarios(String resolucionComentarios) {
        this.resolucionComentarios = resolucionComentarios;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
}
