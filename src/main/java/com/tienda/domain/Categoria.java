/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tienda.domain;

import jakarta.persistence.*;
import lombok.Data;

/**
 *
 * @author jimal
 */
@Data
@Entity
public class Categoria {
    private Long idCategoria;
    private String descripcion;
    private String rutaImagen;
    private boolean activo;
            
            
}

