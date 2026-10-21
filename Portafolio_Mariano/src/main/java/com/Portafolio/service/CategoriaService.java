package com.Portafolio.service;

import com.Portafolio.domain.Categoria;
import com.Portafolio.repository.CategoriaRepository;
import java.io.IOException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final LocalStorageService localStorageService;

    public CategoriaService(
            CategoriaRepository categoriaRepository,
            LocalStorageService localStorageService) {
        this.categoriaRepository = categoriaRepository;
        this.localStorageService = localStorageService;
    }

    @Transactional(readOnly = true)
    public List<Categoria> getCategorias(boolean activo) {
        if (activo) {
            return categoriaRepository.findByActivoTrue();
        }
        return categoriaRepository.findAll();
    }

    @Transactional
    public void save(Categoria categoria, MultipartFile imagenFile)
            throws IOException {

        if (imagenFile != null && !imagenFile.isEmpty()) {
            String rutaImagen =
                    localStorageService.guardarImagen(imagenFile);
            categoria.setRutaImagen(rutaImagen);
        }

        categoriaRepository.save(categoria);
    }

    @Transactional
    public void delete(Categoria categoria) {
        categoriaRepository.delete(categoria);
    }

    @Transactional(readOnly = true)
    public Categoria getCategoria(Categoria categoria) {
        return categoriaRepository.findById(categoria.getIdCategoria())
                .orElse(null);
    }
}