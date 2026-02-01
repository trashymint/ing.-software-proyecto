package com.fundamentos_SW.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Carrito_Producto")
@IdClass(Carrito_ProductoId.class)
public class Carrito_Producto {
    @Id
    @Column(name = "idCarrito")
    private int carritoId;

    @Id
    @Column(name = "idProducto")
    private int productoId;

    private int cantidad;

    // Getters y setters

    public int getCarritoId() {
        return carritoId;
    }

    public void setCarritoId(int carritoId) {
        this.carritoId = carritoId;
    }

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}

