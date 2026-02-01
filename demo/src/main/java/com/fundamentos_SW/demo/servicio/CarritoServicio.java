package com.fundamentos_SW.demo.servicio;

import com.fundamentos_SW.demo.model.*;
import com.fundamentos_SW.demo.repositorio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CarritoServicio {

    @Autowired
    private CarritoRepo carritoRepo;

    @Autowired
    private CarritoProductoRepo carritoProductoRepo;

    @Autowired
    private ProductoRepo productoRepo;

    // Métodos básicos para el Carrito
    public List<Carrito> obtenerTodosCarritos() {
        return carritoRepo.findAll();
    }

    public Optional<Carrito> obtenerCarritoPorId(int id) {
        return carritoRepo.findById(id);
    }

    public Optional<Carrito> obtenerCarritoPorUsuario(int idUsuario) {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(idUsuario);
        return carritoRepo.findByUsuario(usuario);
    }

    public Carrito crearCarrito(Usuario usuario) {
        Carrito carrito = new Carrito();
        carrito.setUsuario(usuario);
        carrito.setTotal(0.0);
        return carritoRepo.save(carrito);
    }

    public void eliminarCarrito(int id) {
        carritoRepo.deleteById(id);
    }

    public Carrito guardarCarrito(Carrito carrito) {
        return carritoRepo.save(carrito);
    }
}
