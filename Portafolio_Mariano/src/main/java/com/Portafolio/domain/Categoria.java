package com.Portafolio.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import lombok.Data;

@Data
@Entity
@Table(name = "categoria")
public class Categoria implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private Integer idCategoria;

    @NotNull
    @Size(max = 50)
    @Column(name = "descripcion", nullable = false, unique = true, length = 50)
    private String descripcion;

    @Size(max = 1024)
    @Column(name = "ruta_imagen", length = 1024)
    private String rutaImagen;

    @Column(name = "activo")
    private Boolean activo;

 
}