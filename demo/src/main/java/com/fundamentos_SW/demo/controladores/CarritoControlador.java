package com.fundamentos_SW.demo.controladores;

import com.fundamentos_SW.demo.servicio.ModificarCarroComprasHU10;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carrito")
public class CarritoControlador {

    @Autowired
    private ModificarCarroComprasHU10 carritoService;

    @PostMapping("/{idCarrito}/usuario/{idUsuario}/productos")
    public ResponseEntity<?> agregarProducto(
            @PathVariable int idCarrito,
            @PathVariable int idUsuario,
            @RequestParam int idProducto,
            @RequestParam int cantidad) {

        try {
            ModificarCarroComprasHU10.ResultadoOperacion resultado =
                    carritoService.agregarProducto(idCarrito, idUsuario, idProducto, cantidad);
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{idCarrito}/usuario/{idUsuario}/productos/{idProducto}")
    public ResponseEntity<?> actualizarCantidad(
            @PathVariable int idCarrito,
            @PathVariable int idUsuario,
            @PathVariable int idProducto,
            @RequestParam int cantidad) {

        try {
            ModificarCarroComprasHU10.ResultadoOperacion resultado =
                    carritoService.actualizarCantidad(idCarrito, idUsuario, idProducto, cantidad);
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{idCarrito}/usuario/{idUsuario}/productos/{idProducto}")
    public ResponseEntity<?> eliminarProducto(
            @PathVariable int idCarrito,
            @PathVariable int idUsuario,
            @PathVariable int idProducto) {

        try {
            ModificarCarroComprasHU10.ResultadoOperacion resultado =
                    carritoService.eliminarProducto(idCarrito, idUsuario, idProducto);
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{idCarrito}/usuario/{idUsuario}/vaciar")
    public ResponseEntity<?> vaciarCarrito(
            @PathVariable int idCarrito,
            @PathVariable int idUsuario) {

        try {
            ModificarCarroComprasHU10.ResultadoOperacion resultado =
                    carritoService.vaciarCarrito(idCarrito, idUsuario);
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}