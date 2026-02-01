package com.fundamentos_SW.demo.repositorio;

import com.fundamentos_SW.demo.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepo extends JpaRepository<Categoria, Integer> {
    boolean existsByNombre(String nombre);

    List<Categoria> findByEstadoOrderByNombreAsc(boolean estado);

    List<Categoria> findAllByOrderByNombreAsc();

    @Query("SELECT COUNT(p) > 0 FROM Producto p WHERE p.categoria.idCategoria = :idCategoria")
    boolean existsProductosRelacionados(int idCategoria);
}

