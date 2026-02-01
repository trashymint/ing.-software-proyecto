package com.fundamentos_SW.demo.controladores;

import com.fundamentos_SW.demo.model.Reporte_Fraude;
import com.fundamentos_SW.demo.servicio.ReporteFraudeHU2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reportes-fraude")
public class ReporteFraudeControlador {

    @Autowired
    private ReporteFraudeHU2 reporteService;

    @PostMapping
    public ResponseEntity<?> crearReporte(@RequestBody Reporte_Fraude reporte) {
        try {
            Reporte_Fraude nuevoReporte = reporteService.crearReporte(reporte);
            return ResponseEntity.ok(nuevoReporte);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public List<Reporte_Fraude> listarReportes(
            @RequestParam(required = false) Boolean resuelto,
            @RequestParam(required = false) Integer idUsuario,
            @RequestParam(required = false) Integer idProducto) {

        if (resuelto != null) {
            return reporteService.obtenerReportesResueltos(resuelto);
        }
        if (idUsuario != null) {
            return reporteService.obtenerPorUsuario(idUsuario);
        }
        if (idProducto != null) {
            return reporteService.obtenerPorProducto(idProducto);
        }
        return reporteService.obtenerTodos();
    }

    @PatchMapping("/{id}/resolver")
    public ResponseEntity<?> resolverReporte(
            @PathVariable int id,
            @RequestParam String comentario,
            @RequestParam int idAdmin) {

        try {
            Reporte_Fraude reporte = reporteService.resolverReporte(id, comentario, idAdmin);
            return ResponseEntity.ok(reporte);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/reabrir")
    public ResponseEntity<?> reabrirReporte(
            @PathVariable int id,
            @RequestParam String motivo) {

        try {
            Reporte_Fraude reporte = reporteService.reabrirReporte(id, motivo);
            return ResponseEntity.ok(reporte);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}