package com.fundamentos_SW.demo.controladores;

import com.fundamentos_SW.demo.model.Producto;
import com.fundamentos_SW.demo.model.Usuario;
import com.fundamentos_SW.demo.model.Categoria;
import com.fundamentos_SW.demo.model.Imagenes;
import com.fundamentos_SW.demo.model.Reporte_Fraude;
import com.fundamentos_SW.demo.repositorio.ProductoRepo;
import com.fundamentos_SW.demo.repositorio.UsuarioRepo;
import com.fundamentos_SW.demo.repositorio.CategoriaRepo;
import com.fundamentos_SW.demo.repositorio.ImagenesRepo;
import com.fundamentos_SW.demo.repositorio.ReporteFraudeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos")
public class ProductoControlador {
    @Autowired
    private ProductoRepo productoRepo;
    @Autowired
    private UsuarioRepo usuarioRepo;
    @Autowired
    private CategoriaRepo categoriaRepo;
    @Autowired
    private ImagenesRepo imagenesRepo;
    @Autowired
    private ReporteFraudeRepo reporteFraudeRepo;

    @PostMapping(consumes = {"multipart/form-data"})
    @Transactional
    public ResponseEntity<?> crearProducto(
            @RequestParam("nombre") String nombre,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("precio") double precio,
            @RequestParam("cantidad") int cantidad,
            @RequestParam("idUsuario") int idUsuario,
            @RequestParam("idCategoria") int idCategoria,
            @RequestParam(value = "imagenes", required = false) List<MultipartFile> imagenes
    ) {
        Optional<Usuario> usuarioOpt = usuarioRepo.findById(idUsuario);
        Optional<Categoria> categoriaOpt = categoriaRepo.findById(idCategoria);
        if (usuarioOpt.isEmpty() || categoriaOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Usuario o categoría no encontrados");
        }
        Producto producto = new Producto();
        producto.setNombre(nombre);
        producto.setDescripcion(descripcion);
        producto.setPrecio(precio);
        producto.setCantidadDisponible(cantidad);
        producto.setEstado(true);
        producto.setUsuario(usuarioOpt.get());
        producto.setCategoria(categoriaOpt.get());
        producto = productoRepo.save(producto);
        // Guardar imágenes si existen
        if (imagenes != null) {
            // Obtener el reporte de fraude con ID 3 (dummy para productos)
            Reporte_Fraude dummyReporte = null;
            Optional<Reporte_Fraude> dummyOpt = reporteFraudeRepo.findById(3);
            if (dummyOpt.isPresent()) {
                dummyReporte = dummyOpt.get();
            }
            for (MultipartFile file : imagenes) {
                try {
                    Imagenes img = new Imagenes();
                    img.setFoto(file.getBytes());
                    img.setProducto(producto);
                    img.setReporteFraude(dummyReporte); // Asignar dummy o null si no existe
                    imagenesRepo.save(img);
                } catch (Exception e) {
                    return ResponseEntity.internalServerError().body("Error guardando imagen: " + e.getMessage());
                }
            }
        }
        return ResponseEntity.ok(producto);
    }

    @GetMapping("/con-imagenes")
    public ResponseEntity<?> getProductosConImagenes() {
        List<Producto> productos = productoRepo.findAll();
        List<java.util.Map<String, Object>> result = productos.stream().map(producto -> {
            List<Imagenes> imagenes = imagenesRepo.findByProducto_IdProducto(producto.getIdProducto());
            List<String> imagenesBase64 = imagenes.stream()
                .map(img -> java.util.Base64.getEncoder().encodeToString(img.getFoto()))
                .toList();
            return java.util.Map.of(
                "idProducto", producto.getIdProducto(),
                "nombre", producto.getNombre(),
                "precio", producto.getPrecio(),
                "imagenes", imagenesBase64
            );
        }).toList();
        return ResponseEntity.ok(result);
    }
}
