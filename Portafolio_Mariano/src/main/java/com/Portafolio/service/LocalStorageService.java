package com.Portafolio.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class LocalStorageService {

    private final Path directorio = Paths.get("uploads");

    public String guardarImagen(MultipartFile archivo)
            throws IOException {

        if (archivo == null || archivo.isEmpty()) {
            return null;
        }

        Files.createDirectories(directorio);

        String nombreOriginal = archivo.getOriginalFilename();
        String extension = "";

        if (nombreOriginal != null && nombreOriginal.contains(".")) {
            extension = nombreOriginal.substring(
                    nombreOriginal.lastIndexOf(".")
            ).toLowerCase();
        }

        String nombreArchivo = UUID.randomUUID() + extension;
        Path destino = directorio.resolve(nombreArchivo).normalize();

        if (!destino.startsWith(directorio.toAbsolutePath().normalize())
                && destino.isAbsolute()) {
            throw new IOException("Ruta de archivo no válida");
        }

        archivo.transferTo(destino.toFile());

        return "/uploads/" + nombreArchivo;
    }
}