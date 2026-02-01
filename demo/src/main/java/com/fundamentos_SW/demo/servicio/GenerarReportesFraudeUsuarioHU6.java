package com.fundamentos_SW.demo.servicio;

import com.fundamentos_SW.demo.model.Reporte_Fraude;
import com.fundamentos_SW.demo.model.Usuario;
import com.fundamentos_SW.demo.model.Producto;
import com.fundamentos_SW.demo.repositorio.ReporteFraudeRepo;
import com.fundamentos_SW.demo.repositorio.UsuarioRepo;
import com.fundamentos_SW.demo.repositorio.ProductoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class GenerarReportesFraudeUsuarioHU6 {

    @Autowired
    private ReporteFraudeRepo reporteRepo;

    @Autowired
    private UsuarioRepo usuarioRepo;

    @Autowired
    private ProductoRepo productoRepo;

    // Tipos de reporte predefinidos (pueden ser enums en una versión más avanzada)
    private static final String TIPO_SUPLANTACION = "Suplantación";
    private static final String TIPO_PRODUCTO_FALSO = "Producto falso";
    private static final String TIPO_INFORMACION_FALSA = "Información falsa";
    private static final boolean ESTADO_PENDIENTE = false;

    @Transactional
    public Reporte_Fraude generarReporte(int idUsuario, int idProducto, String tipo, String descripcion) {
        // Validaciones de entrada
        validarDatosReporte(tipo, descripcion);

        // Obtener y validar usuario y producto
        Usuario usuario = usuarioRepo.findById(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el usuario con ID: " + idUsuario));

        Producto producto = productoRepo.findById(idProducto)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el producto con ID: " + idProducto));

        // Crear y guardar el reporte
        Reporte_Fraude reporte = new Reporte_Fraude();
        reporte.setUsuario(usuario);
        reporte.setProducto(producto);
        reporte.setTipo(tipo);
        reporte.setDescripcion(descripcion);
        reporte.setEstado(ESTADO_PENDIENTE);
        reporte.setResolucionComentarios(null);

        Reporte_Fraude reporteGuardado = reporteRepo.save(reporte);

        return reporteGuardado;
    }

    public List<Reporte_Fraude> obtenerReportesPorUsuario(int idUsuario) {
        if (!usuarioRepo.existsById(idUsuario)) {
            throw new IllegalArgumentException("No existe usuario con ID: " + idUsuario);
        }
        return reporteRepo.findByUsuarioIdUsuario(idUsuario);
    }

    // Métodos de validación privados
    private void validarDatosReporte(String tipo, String descripcion) {
        if (!StringUtils.hasText(tipo)) {
            throw new IllegalArgumentException("El tipo de reporte es obligatorio");
        }
        if (!StringUtils.hasText(descripcion)) {
            throw new IllegalArgumentException("La descripción es obligatoria");
        }
        if (descripcion.length() < 20) {
            throw new IllegalArgumentException("La descripción debe tener al menos 20 caracteres");
        }
        if (!List.of(TIPO_SUPLANTACION, TIPO_PRODUCTO_FALSO, TIPO_INFORMACION_FALSA).contains(tipo)) {
            throw new IllegalArgumentException("Tipo de reporte no válido");
        }
    }
    private void notificarAdministrador(Reporte_Fraude reporte) {
        // Implementar lógica de notificación (email, websocket, etc.)
    }
}
