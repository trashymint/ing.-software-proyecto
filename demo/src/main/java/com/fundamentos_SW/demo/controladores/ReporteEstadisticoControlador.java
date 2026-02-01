package com.fundamentos_SW.demo.controladores;

import com.fundamentos_SW.demo.model.Reporte_Estadistico;
import com.fundamentos_SW.demo.servicio.ReporteEstadisticoHU4;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/reportes-estadisticos")
public class ReporteEstadisticoControlador {

    @Autowired
    private ReporteEstadisticoHU4 reporteService;

    @PostMapping
    public ResponseEntity<?> crearReporte(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin,
            @RequestParam int productosVendidos,
            @RequestParam double ganancias,
            @RequestParam int idProducto) {

        try {
            Reporte_Estadistico reporte = reporteService.crearReporte(
                    inicio, fin, productosVendidos, ganancias, idProducto);
            return ResponseEntity.ok(reporte);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public List<Reporte_Estadistico> listarReportes(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta,
            @RequestParam(required = false) Integer idProducto) {

        if (desde != null && hasta != null) {
            return reporteService.buscarPorFechas(desde, hasta);
        }
        if (idProducto != null) {
            return reporteService.buscarPorProducto(idProducto);
        }
        return reporteService.listarTodos();
    }

    @PatchMapping("/{id}/finalizar")
    public ResponseEntity<?> finalizarReporte(
            @PathVariable int id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin,
            @RequestParam int productosVendidos,
            @RequestParam double ganancias) {

        try {
            Reporte_Estadistico reporte = reporteService.finalizarReporte(
                    id, fechaFin, productosVendidos, ganancias);
            return ResponseEntity.ok(reporte);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
