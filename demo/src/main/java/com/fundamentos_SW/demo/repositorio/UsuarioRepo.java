package com.fundamentos_SW.demo.repositorio;

import com.fundamentos_SW.demo.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepo extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByCorreo(String correo);
    Optional<Usuario> findByIdInstitucional(String idInstitucional);
    Optional<Usuario> findByCedula(String cedula);

    boolean existsByCorreo(String correo);
    boolean existsByCedula(String cedula);
    boolean existsByIdInstitucional(String idInstitucional);
}

