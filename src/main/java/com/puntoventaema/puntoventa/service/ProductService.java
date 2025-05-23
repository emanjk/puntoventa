package com.puntoventaema.puntoventa.service;

import com.puntoventaema.puntoventa.model.Product;
import java.util.List;

public interface ProductService {

    //métodos abstractos (sin implementación)

    Product save(Product product); //"save" hace el alta y también la actualización
    Product findById(Long id);
    void delete(Long id);
    List<Product> findAll(); //muestra una lista de productos activos
    List<Product> findByActivoFalse(); //muestra una lista de productos inactivos
    Product findByCodigoBarras(String codigoBarras);
    List<Product> findByMarca(String marca);

}
