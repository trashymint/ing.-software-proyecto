package com.fundamentos_SW.demo.model;

import java.io.Serializable;
import java.util.Objects;

public class Carrito_ProductoId implements Serializable {
    private int carritoId;
    private int productoId;

    public Carrito_ProductoId(int idCarrito, int idProducto) {

    }

    // equals() y hashCode()

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Carrito_ProductoId that)) return false;
        return carritoId == that.carritoId && productoId == that.productoId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(carritoId, productoId);
    }
}
