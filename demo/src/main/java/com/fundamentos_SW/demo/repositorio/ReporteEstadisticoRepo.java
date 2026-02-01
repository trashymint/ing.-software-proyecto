package com.fundamentos_SW.demo.repositorio;

import com.fundamentos_SW.demo.model.Reporte_Estadistico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReporteEstadisticoRepo extends JpaRepository<Reporte_Estadistico, Integer> {
    List<Reporte_Estadistico> findByFechaInicioBetween(LocalDateTime desde, LocalDateTime hasta);
    List<Reporte_Estadistico> findByProductoIdProducto(int idProducto);
    List<Reporte_Estadistico> findByFechaFinIsNull();
}

