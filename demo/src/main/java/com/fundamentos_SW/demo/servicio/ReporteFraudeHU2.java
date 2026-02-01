package com.fundamentos_SW.demo.servicio;

import com.fundamentos_SW.demo.model.Reporte_Fraude;
import com.fundamentos_SW.demo.model.Usuario;
import com.fundamentos_SW.demo.repositorio.ReporteFraudeRepo;
import com.fundamentos_SW.demo.repositorio.UsuarioRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class ReporteFraudeHU2 {

    @Autowired
    private ReporteFraudeRepo reporteRepo;

    @Autowired
    private UsuarioRepo usuarioRepo;

    @Transactional
    public Reporte_Fraude crearReporte(Reporte_Fraude reporte) {
        if (!validarReporte(reporte)) {
            throw new IllegalArgumentException("Todos los campos del reporte son obligatorios");
        }

        reporte.setEstado(false); // Por defecto no resuelto
        return reporteRepo.save(reporte);
    }

    public List<Reporte_Fraude> obtenerTodos() {
        return reporteRepo.findAll();
    }

    public List<Reporte_Fraude> obtenerReportesResueltos(boolean resuelto) {
        return reporteRepo.findByEstado(resuelto);
    }

    public List<Reporte_Fraude> obtenerPorUsuario(int idUsuario) {
        return reporteRepo.findByUsuarioIdUsuario(idUsuario);
    }

    public List<Reporte_Fraude> obtenerPorProducto(int idProducto) {
        return reporteRepo.findByProductoIdProducto(idProducto);
    }

    @Transactional
    public Reporte_Fraude resolverReporte(int idReporte, String comentario, int idAdmin) {
        if (!StringUtils.hasText(comentario)) {
            throw new IllegalArgumentException("El comentario de resolución es obligatorio");
        }

        Usuario admin = usuarioRepo.findById(idAdmin)
                .orElseThrow(() -> new IllegalArgumentException("Usuario administrador no encontrado"));

        Reporte_Fraude reporte = reporteRepo.findById(idReporte)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el reporte con ID: " + idReporte));

        if (reporte.isEstado()) {
            throw new IllegalStateException("El reporte ya está marcado como resuelto");
        }

        reporte.setEstado(true);
        reporte.setResolucionComentarios(comentario);
        // Podrías registrar también quién resolvió el reporte
        // reporte.setAdminResolucion(admin);

        return reporteRepo.save(reporte);
    }

    @Transactional
    public Reporte_Fraude reabrirReporte(int idReporte, String motivo) {
        Reporte_Fraude reporte = reporteRepo.findById(idReporte)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el reporte con ID: " + idReporte));

        if (!reporte.isEstado()) {
            throw new IllegalStateException("El reporte no está resuelto");
        }

        reporte.setEstado(false);
        reporte.setResolucionComentarios(
                (reporte.getResolucionComentarios() != null ? reporte.getResolucionComentarios() + "\n" : "") +
                        "Reabierto - Motivo: " + motivo
        );

        return reporteRepo.save(reporte);
    }

    private boolean validarReporte(Reporte_Fraude reporte) {
        return reporte != null &&
                StringUtils.hasText(reporte.getDescripcion()) &&
                StringUtils.hasText(reporte.getTipo()) &&
                reporte.getUsuario() != null &&
                reporte.getProducto() != null;
    }
}
