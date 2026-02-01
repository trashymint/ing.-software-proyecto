package com.fundamentos_SW.demo.controladores;

import com.fundamentos_SW.demo.model.Categoria;
import com.fundamentos_SW.demo.servicio.GestionarCategoriasProductosHU3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gestion-categorias")
public class CategoriasControlador {

    @Autowired
    private GestionarCategoriasProductosHU3 gestionCategoriasService;

    @PostMapping
    public ResponseEntity<?> crearCategoria(
            @RequestParam String nombre,
            @RequestParam String descripcion,
            @RequestParam(required = false, defaultValue = "true") boolean estado) {

        try {
            Categoria nuevaCategoria = gestionCategoriasService.crearCategoria(nombre, descripcion, estado);
            return ResponseEntity.status(201).body(nuevaCategoria);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editarCategoria(
            @PathVariable int id,
            @RequestParam String nombre,
            @RequestParam String descripcion,
            @RequestParam(required = false) Boolean estado) {

        try {
            Categoria categoriaExistente = gestionCategoriasService.obtenerPorId(id)
                    .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));

            // Si no se proporciona estado, mantener el actual
            boolean nuevoEstado = estado != null ? estado : categoriaExistente.isEstado();

            Categoria categoriaActualizada = gestionCategoriasService.editarCategoria(
                    id, nombre, descripcion, nuevoEstado);
            return ResponseEntity.ok(categoriaActualizada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public List<Categoria> listarTodasCategorias() {
        return gestionCategoriasService.listarTodasCategorias();
    }

    @GetMapping("/activas")
    public List<Categoria> listarCategoriasActivas() {
        return gestionCategoriasService.listarCategoriasActivas();
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<?> cambiarEstadoCategoria(
            @PathVariable int id,
            @RequestParam boolean estado) {

        try {
            Categoria categoriaActualizada = gestionCategoriasService.cambiarEstadoCategoria(id, estado);
            return ResponseEntity.ok(categoriaActualizada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCategoria(@PathVariable int id) {
        try {
            gestionCategoriasService.eliminarCategoria(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }
}