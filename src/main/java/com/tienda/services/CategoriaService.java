package com.tienda.services;

import com.tienda.domain.Categoria;
import java.util.List;


public interface CategoriaService {
    //se define la firma del metodo para el arraylist de categoria, objetos de tipo categogía, todos los registros o todos los activos
    public List<Categoria> getCategorias(boolean activos);
    
   // Se obtiene un Categoria, a partir del id de un categoria
    public Categoria getCategoria(Categoria categoria);
    
    // Se inserta un nuevo categoria si el id del categoria esta vacío
    // Se actualiza un categoria si el id del categoria NO esta vacío
    public void save(Categoria categoria);
    
    // Se elimina el categoria que tiene el id pasado por parámetro
    public void delete(Categoria categoria);

}
