package com.fundamentos_SW.demo.repositorio;

import com.fundamentos_SW.demo.model.Bloqueo;
import com.fundamentos_SW.demo.model.Producto;
import com.fundamentos_SW.demo.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BloqueoRepo extends JpaRepository<Bloqueo, Integer> {
    List<Bloqueo> findByAdmin(Usuario admin);
    List<Bloqueo> findByProducto(Producto producto);
    List<Bloqueo> findByProductoIdProducto(int idProducto);
    List<Bloqueo> findByEstado(boolean estado);
}

