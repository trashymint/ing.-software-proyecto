package com.fundamentos_SW.demo.repositorio;

import com.fundamentos_SW.demo.model.Carrito;
import com.fundamentos_SW.demo.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarritoRepo extends JpaRepository<Carrito, Integer> {
    Optional<Carrito> findByUsuario(Usuario usuario);
}

