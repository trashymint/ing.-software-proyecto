package com.fundamentos_SW.demo.repositorio;

import com.fundamentos_SW.demo.model.Imagenes;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ImagenesRepo extends JpaRepository<Imagenes, Integer> {
    List<Imagenes> findByProducto_IdProducto(int idProducto);
}
