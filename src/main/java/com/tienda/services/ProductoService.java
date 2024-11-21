package com.tienda.services;

import com.tienda.domain.Producto;
import java.util.List;


public interface ProductoService {
    //se define la firma del metodo para el arraylist de producto, objetos de tipo categogía, todos los registros o todos los activos
    public List<Producto> getProductos(boolean activos);
    
   // Se obtiene un Producto, a partir del id de un producto
    public Producto getProducto(Producto producto);
    
    // Se inserta un nuevo producto si el id del producto esta vacío
    // Se actualiza un producto si el id del producto NO esta vacío
    public void save(Producto producto);
    
    // Se elimina el producto que tiene el id pasado por parámetro
    public void delete(Producto producto);

}
