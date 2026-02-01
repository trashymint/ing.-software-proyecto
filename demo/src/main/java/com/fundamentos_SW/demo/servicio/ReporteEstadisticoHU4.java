package com.fundamentos_SW.demo.servicio;

import com.fundamentos_SW.demo.model.Producto;
import com.fundamentos_SW.demo.model.Reporte_Estadistico;
import com.fundamentos_SW.demo.repositorio.ProductoRepo;
import com.fundamentos_SW.demo.repositorio.ReporteEstadisticoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReporteEstadisticoHU4 {

    @Autowired
    private ReporteEstadisticoRepo reporteRepo;

    @Autowired
    private ProductoRepo productoRepo;

    @Transactional
    public Reporte_Estadistico crearReporte(LocalDateTime inicio, LocalDateTime fin,
                                            int productosVendidos, double ganancias,
                                            int idProducto) {
        validarFechas(inicio, fin);
        validarDatosReporte(productosVendidos, ganancias);

        Producto producto = productoRepo.findById(idProducto)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con ID: " + idProducto));

        Reporte_Estadistico reporte = new Reporte_Estadistico();
        reporte.setFechaInicio(inicio);
        reporte.setFechaFin(fin);
        reporte.setProductosVendidos(productosVendidos);
        reporte.setGanancias(ganancias);
        reporte.setProducto(producto);

        return reporteRepo.save(reporte);
    }

    @Transactional
    public Reporte_Estadistico finalizarReporte(int idReporte, LocalDateTime fechaFin,
                                                int productosVendidos, double ganancias) {
        Reporte_Estadistico reporte = reporteRepo.findById(idReporte)
                .orElseThrow(() -> new IllegalArgumentException("Reporte no encontrado con ID: " + idReporte));

        validarDatosReporte(productosVendidos, ganancias);

        if (fechaFin.isBefore(reporte.getFechaInicio())) {
            throw new IllegalArgumentException("La fecha final no puede ser anterior a la fecha inicial");
        }

        reporte.setFechaFin(fechaFin);
        reporte.setProductosVendidos(productosVendidos);
        reporte.setGanancias(ganancias);

        return reporteRepo.save(reporte);
    }

    public List<Reporte_Estadistico> listarTodos() {
        return reporteRepo.findAll();
    }

    public List<Reporte_Estadistico> buscarPorFechas(LocalDateTime desde, LocalDateTime hasta) {
        validarFechas(desde, hasta);
        return reporteRepo.findByFechaInicioBetween(desde, hasta);
    }

    public List<Reporte_Estadistico> buscarPorProducto(int idProducto) {
        if (!productoRepo.existsById(idProducto)) {
            throw new IllegalArgumentException("No existe producto con ID: " + idProducto);
        }
        return reporteRepo.findByProductoIdProducto(idProducto);
    }

    public List<Reporte_Estadistico> buscarReportesActivos() {
        return reporteRepo.findByFechaFinIsNull();
    }

    // Métodos de validación privados
    private void validarFechas(LocalDateTime inicio, LocalDateTime fin) {
        if (inicio == null || fin == null) {
            throw new IllegalArgumentException("Las fechas no pueden ser nulas");
        }
        if (fin.isBefore(inicio)) {
            throw new IllegalArgumentException("La fecha final no puede ser anterior a la fecha inicial");
        }
    }

    private void validarDatosReporte(int productosVendidos, double ganancias) {
        if (productosVendidos < 0) {
            throw new IllegalArgumentException("Los productos vendidos no pueden ser negativos");
        }
        if (ganancias < 0) {
            throw new IllegalArgumentException("Las ganancias no pueden ser negativas");
        }
    }
}
