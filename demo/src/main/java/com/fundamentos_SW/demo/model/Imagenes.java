package com.fundamentos_SW.demo.model;

import jakarta.persistence.*;
@Entity
@Table(name = "Imagenes")
public class Imagenes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idImagen;

    @Lob
    private byte[] foto;

    @ManyToOne
    @JoinColumn(name = "idProducto")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "idReporteFraude")
    private Reporte_Fraude reporteFraude;

    // Getters y Setters

    public int getIdImagen() {
        return idImagen;
    }

    public void setIdImagen(int idImagen) {
        this.idImagen = idImagen;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Reporte_Fraude getReporteFraude() {
        return reporteFraude;
    }

    public void setReporteFraude(Reporte_Fraude reporteFraude) {
        this.reporteFraude = reporteFraude;
    }
}
