package com.fundamentos_SW.demo.repositorio;

import com.fundamentos_SW.demo.model.Reporte_Fraude;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReporteFraudeRepo extends JpaRepository<Reporte_Fraude, Integer> {
    List<Reporte_Fraude> findByEstado(boolean estado);
    List<Reporte_Fraude> findByUsuarioIdUsuario(int idUsuario);
    List<Reporte_Fraude> findByProductoIdProducto(int idProducto);
}
