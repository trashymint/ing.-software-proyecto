package com.fundamentos_SW.demo.repositorio;

import com.fundamentos_SW.demo.model.Categoria;
import com.fundamentos_SW.demo.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductoRepo extends JpaRepository<Producto, Integer> {


}
