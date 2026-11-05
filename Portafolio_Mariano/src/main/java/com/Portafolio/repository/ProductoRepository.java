
package com.Portafolio.repository;

import com.Portafolio.domain.Producto;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    public List<Producto> findByActivoTrue();

    // Consulta derivada
    public List<Producto> findByPrecioBetweenOrderByPrecioAsc(
            BigDecimal precioInf,
            BigDecimal precioSup);

    // Consulta JPQL
    @Query("SELECT p FROM Producto p "
            + "WHERE p.precio BETWEEN :precioInf AND :precioSup "
            + "ORDER BY p.precio ASC")
    public List<Producto> consultaJPQL(
            @Param("precioInf") BigDecimal precioInf,
            @Param("precioSup") BigDecimal precioSup);

    // Consulta SQL nativa
    @Query(value = "SELECT * FROM producto "
            + "WHERE precio BETWEEN :precioInf AND :precioSup "
            + "ORDER BY precio ASC",
            nativeQuery = true)
    public List<Producto> consultaSQL(
            @Param("precioInf") BigDecimal precioInf,
            @Param("precioSup") BigDecimal precioSup);
}
