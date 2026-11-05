
package com.Portafolio.controller;

import com.Portafolio.domain.Categoria;
import com.Portafolio.domain.Producto;
import com.Portafolio.service.CategoriaService;
import com.Portafolio.service.ProductoService;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class IndexController {

    private final ProductoService productoService;
    private final CategoriaService categoriaService;

    public IndexController(
            ProductoService productoService,
            CategoriaService categoriaService) {
        this.productoService = productoService;
        this.categoriaService = categoriaService;
    }

    @GetMapping("/")
    public String cargarPaginaInicio(Model model) {

        model.addAttribute(
                "productos",
                productoService.getProductos(true));

        model.addAttribute(
                "categorias",
                categoriaService.getCategorias(true));

        return "index";
    }

    @GetMapping("/consultas/{idCategoria}")
    public String listado(
            @PathVariable("idCategoria") Integer idCategoria,
            Model model) {

        model.addAttribute("idCategoriaActual", idCategoria);

        Categoria categoriaBusqueda = new Categoria();
        categoriaBusqueda.setIdCategoria(idCategoria);

        Categoria categoria =
                categoriaService.getCategoria(categoriaBusqueda);

        List<Producto> productos = categoria != null
                ? categoria.getProductos()
                : Collections.emptyList();

        model.addAttribute("productos", productos);

        model.addAttribute(
                "categorias",
                categoriaService.getCategorias(true));

        return "index";
    }
}
