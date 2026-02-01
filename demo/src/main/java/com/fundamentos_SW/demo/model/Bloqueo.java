package com.fundamentos_SW.demo.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
@Entity
@Table(name = "Bloqueo")
public class Bloqueo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idBloqueo;

    @Column(name = "FechaBloqueo")
    private LocalDateTime fechaBloqueo;

    private String motivo;
    private boolean estado;

    @ManyToOne
    @JoinColumn(name = "idAdmin")
    private Usuario admin;

    @ManyToOne
    @JoinColumn(name = "idProducto")
    private Producto producto;
    // Getters y Setters

    public int getIdBloqueo() {
        return idBloqueo;
    }

    public void setIdBloqueo(int idBloqueo) {
        this.idBloqueo = idBloqueo;
    }

    public LocalDateTime getFechaBloqueo() {
        return fechaBloqueo;
    }

    public void setFechaBloqueo(LocalDateTime fechaBloqueo) {
        this.fechaBloqueo = fechaBloqueo;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Usuario getAdmin() {
        return admin;
    }

    public void setAdmin(Usuario admin) {
        this.admin = admin;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
}
