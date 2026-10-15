package com.Portafolio.service;

import com.Portafolio.domain.Producto;
import com.Portafolio.repository.ProductoRepository;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final LocalStorageService localStorageService;

    public ProductoService(
            ProductoRepository productoRepository,
            LocalStorageService localStorageService) {
        this.productoRepository = productoRepository;
        this.localStorageService = localStorageService;
    }

    @Transactional(readOnly = true)
    public List<Producto> getProductos(boolean activo) {
        if (activo) {
            return productoRepository.findByActivoTrue();
        }
        return productoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Producto> getProducto(Integer idProducto) {
        return productoRepository.findById(idProducto);
    }

    @Transactional
    public void save(Producto producto, MultipartFile imagenFile) {
        // Guardamos primero para obtener el ID del producto.
        producto = productoRepository.save(producto);

        // Solo actualizamos la imagen si se seleccionó una nueva.
        if (imagenFile != null && !imagenFile.isEmpty()) {
            try {
                String rutaImagen =
                        localStorageService.guardarImagen(imagenFile);

                producto.setRutaImagen(rutaImagen);
                productoRepository.save(producto);

            } catch (IOException e) {
                throw new IllegalStateException(
                        "No se pudo guardar la imagen del producto.", e);
            }
        }
    }

    @Transactional
    public void delete(Integer idProducto) {
        if (!productoRepository.existsById(idProducto)) {
            throw new IllegalArgumentException(
                    "El producto con ID " + idProducto + " no existe.");
        }

        try {
            productoRepository.deleteById(idProducto);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException(
                    "No se puede eliminar el producto porque tiene datos asociados.",
                    e);
        }
    }

}