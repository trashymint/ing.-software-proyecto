package com.fundamentos_SW.demo.servicio;

import com.fundamentos_SW.demo.model.Categoria;
import com.fundamentos_SW.demo.repositorio.CategoriaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class GestionarCategoriasProductosHU3 {

    @Autowired
    private CategoriaRepo categoriaRepo;

    // Estados como constantes booleanas
    private static final boolean ESTADO_INACTIVO = false;
    private static final boolean ESTADO_ACTIVO = true;

    @Transactional
    public Categoria crearCategoria(String nombre, String descripcion, boolean estado) {
        validarDatosCategoria(nombre, descripcion);

        if (categoriaRepo.existsByNombre(nombre)) {
            throw new IllegalArgumentException("Ya existe una categoría con ese nombre");
        }

        Categoria categoria = new Categoria();
        categoria.setNombre(nombre);
        categoria.setDescripcion(descripcion);
        categoria.setEstado(estado);

        return categoriaRepo.save(categoria);
    }

    @Transactional
    public Categoria editarCategoria(int idCategoria, String nuevoNombre, String nuevaDescripcion, boolean nuevoEstado) {
        validarDatosCategoria(nuevoNombre, nuevaDescripcion);

        Categoria categoria = categoriaRepo.findById(idCategoria)
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada con ID: " + idCategoria));

        if (!categoria.getNombre().equals(nuevoNombre) && categoriaRepo.existsByNombre(nuevoNombre)) {
            throw new IllegalArgumentException("Ya existe otra categoría con ese nombre");
        }

        categoria.setNombre(nuevoNombre);
        categoria.setDescripcion(nuevaDescripcion);
        categoria.setEstado(nuevoEstado);

        return categoriaRepo.save(categoria);
    }

    public Optional<Categoria> obtenerPorId(int id) {
        return categoriaRepo.findById(id);
    }
    public List<Categoria> listarCategoriasActivas() {
        return categoriaRepo.findByEstadoOrderByNombreAsc(ESTADO_ACTIVO);
    }

    public List<Categoria> listarTodasCategorias() {
        return categoriaRepo.findAllByOrderByNombreAsc();
    }

    @Transactional
    public Categoria cambiarEstadoCategoria(int idCategoria, boolean nuevoEstado) {
        Categoria categoria = categoriaRepo.findById(idCategoria)
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada con ID: " + idCategoria));

        categoria.setEstado(nuevoEstado);
        return categoriaRepo.save(categoria);
    }

    @Transactional
    public void eliminarCategoria(int idCategoria) {
        // Verificar si la categoría está siendo usada por productos antes de eliminar
        if (categoriaRepo.existsProductosRelacionados(idCategoria)) {
            throw new IllegalStateException("No se puede eliminar la categoría porque tiene productos asociados");
        }

        categoriaRepo.deleteById(idCategoria);
    }

    // Métodos de validación privados
    private void validarDatosCategoria(String nombre, String descripcion) {
        if (!StringUtils.hasText(nombre)) {
            throw new IllegalArgumentException("El nombre de la categoría es requerido");
        }
        if (!StringUtils.hasText(descripcion)) {
            throw new IllegalArgumentException("La descripción de la categoría es requerida");
        }
        if (nombre.length() > 100) { // Coincide con el tamaño en la BD
            throw new IllegalArgumentException("El nombre no puede exceder los 100 caracteres");
        }
        if (descripcion.length() > 400) { // Coincide con el tamaño en la BD
            throw new IllegalArgumentException("La descripción no puede exceder los 400 caracteres");
        }
    }
}
