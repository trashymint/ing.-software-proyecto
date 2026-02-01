package com.fundamentos_SW.demo.repositorio;

import com.fundamentos_SW.demo.model.Carrito;
import com.fundamentos_SW.demo.model.Carrito_Producto;
import com.fundamentos_SW.demo.model.Carrito_ProductoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarritoProductoRepo extends JpaRepository<Carrito_Producto, Carrito_ProductoId> {
    List<Carrito_Producto> findByCarritoId(int carritoId);
    void deleteByCarritoId(int carritoId);
}
