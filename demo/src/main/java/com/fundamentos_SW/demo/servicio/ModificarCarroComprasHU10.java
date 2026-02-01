package com.fundamentos_SW.demo.servicio;

import com.fundamentos_SW.demo.model.*;
import com.fundamentos_SW.demo.repositorio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ModificarCarroComprasHU10 {

    @Autowired
    private CarritoProductoRepo carritoProductoRepo;

    @Autowired
    private ProductoRepo productoRepo;

    @Autowired
    private CarritoServicio carritoServicio;

    @Autowired
    private UsuarioRepo usuarioRepo; // Necesario para validaciones

    private static final double IVA = 0.19;

    @Transactional
    public ResultadoOperacion agregarProducto(int idCarrito, int idUsuario, int idProducto, int cantidad) {
        // Validaciones básicas
        if (cantidad <= 0) {
            return new ResultadoOperacion(false, "La cantidad debe ser mayor a cero");
        }

        // Verificar existencia del producto
        Producto producto = productoRepo.findById(idProducto)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con ID: " + idProducto));

        // Verificar stock
        if (producto.getCantidadDisponible() < cantidad) {
            return new ResultadoOperacion(false, "No hay suficiente stock disponible. Stock actual: " + producto.getCantidadDisponible());
        }

        // Verificar si el carrito pertenece al usuario
        Carrito carrito = carritoServicio.obtenerCarritoPorId(idCarrito)
                .orElseThrow(() -> new IllegalArgumentException("Carrito no encontrado"));

        if (carrito.getUsuario().getIdUsuario() != idUsuario) {
            throw new SecurityException("No tiene permisos para modificar este carrito");
        }

        // Verificar si el producto ya está en el carrito
        Carrito_ProductoId id = new Carrito_ProductoId(idCarrito, idProducto);
        carritoProductoRepo.findById(id).ifPresent(item -> {
            throw new IllegalStateException("Este producto ya está en el carrito. Use actualizar cantidad en lugar de agregar");
        });

        // Crear y guardar el nuevo item
        Carrito_Producto nuevoItem = new Carrito_Producto();
        nuevoItem.setCarritoId(idCarrito);
        nuevoItem.setProductoId(idProducto);
        nuevoItem.setCantidad(cantidad);

        carritoProductoRepo.save(nuevoItem);
        actualizarTotalCarrito(idCarrito);

        return new ResultadoOperacion(true, "Producto agregado exitosamente al carrito");
    }

    @Transactional
    public ResultadoOperacion actualizarCantidad(int idCarrito, int idUsuario, int idProducto, int nuevaCantidad) {
        // Validaciones básicas
        if (nuevaCantidad <= 0) {
            return new ResultadoOperacion(false, "La cantidad debe ser mayor a cero");
        }

        // Verificar existencia del producto
        Producto producto = productoRepo.findById(idProducto)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con ID: " + idProducto));

        // Verificar stock
        if (producto.getCantidadDisponible() < nuevaCantidad) {
            return new ResultadoOperacion(false, "No hay suficiente stock disponible. Stock actual: " + producto.getCantidadDisponible());
        }

        // Verificar permisos del carrito
        Carrito carrito = carritoServicio.obtenerCarritoPorId(idCarrito)
                .orElseThrow(() -> new IllegalArgumentException("Carrito no encontrado"));

        if (carrito.getUsuario().getIdUsuario() != idUsuario) {
            throw new SecurityException("No tiene permisos para modificar este carrito");
        }

        // Buscar y actualizar el item
        Carrito_ProductoId id = new Carrito_ProductoId(idCarrito, idProducto);
        Carrito_Producto item = carritoProductoRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado en el carrito"));

        item.setCantidad(nuevaCantidad);
        carritoProductoRepo.save(item);
        actualizarTotalCarrito(idCarrito);

        return new ResultadoOperacion(true, "Cantidad actualizada exitosamente");
    }

    @Transactional
    public ResultadoOperacion eliminarProducto(int idCarrito, int idUsuario, int idProducto) {
        // Verificar permisos del carrito
        Carrito carrito = carritoServicio.obtenerCarritoPorId(idCarrito)
                .orElseThrow(() -> new IllegalArgumentException("Carrito no encontrado"));

        if (carrito.getUsuario().getIdUsuario() != idUsuario) {
            throw new SecurityException("No tiene permisos para modificar este carrito");
        }

        Carrito_ProductoId id = new Carrito_ProductoId(idCarrito, idProducto);

        if (!carritoProductoRepo.existsById(id)) {
            return new ResultadoOperacion(false, "Este producto no está en el carrito");
        }

        carritoProductoRepo.deleteById(id);
        actualizarTotalCarrito(idCarrito);

        return new ResultadoOperacion(true, "Producto eliminado del carrito");
    }

    @Transactional
    public ResultadoOperacion vaciarCarrito(int idCarrito, int idUsuario) {
        Carrito carrito = carritoServicio.obtenerCarritoPorId(idCarrito)
                .orElseThrow(() -> new IllegalArgumentException("Carrito no encontrado"));

        if (carrito.getUsuario().getIdUsuario() != idUsuario) {
            throw new SecurityException("No tiene permisos para modificar este carrito");
        }

        carritoProductoRepo.deleteByCarritoId(idCarrito);
        carrito.setTotal(0.0);
        carritoServicio.guardarCarrito(carrito);

        return new ResultadoOperacion(true, "Carrito vaciado exitosamente");
    }

    private void actualizarTotalCarrito(int idCarrito) {
        List<Carrito_Producto> items = carritoProductoRepo.findByCarritoId(idCarrito);
        double total = items.stream()
                .mapToDouble(item -> {
                    Producto p = productoRepo.findById(item.getProductoId())
                            .orElseThrow(() -> new IllegalStateException("Producto no encontrado"));
                    return (p.getPrecio() * item.getCantidad()) * (1 + IVA);
                })
                .sum();

        Carrito carrito = carritoServicio.obtenerCarritoPorId(idCarrito)
                .orElseThrow(() -> new IllegalStateException("Carrito no encontrado"));
        carrito.setTotal(total);
        carritoServicio.guardarCarrito(carrito);
    }

    // Clase para manejar resultados de operaciones
    public static class ResultadoOperacion {
        private boolean exito;
        private String mensaje;

        public ResultadoOperacion(boolean exito, String mensaje) {
            this.exito = exito;
            this.mensaje = mensaje;
        }

        // Getters
        public boolean isExito() { return exito; }
        public String getMensaje() { return mensaje; }
    }
}
