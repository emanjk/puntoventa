package com.puntoventaema.puntoventa.service;

import com.puntoventaema.puntoventa.model.Sale;

import java.util.List;

public interface SaleService {

    //métodos abstractos (sin implementación)
    Sale save(Sale sale); //guardar una venta (alta)
    Sale findById(Long id); //buscar venta por id
    void delete(Long id); //eliminar una venta (baja)
    List<Sale> findAll(); //listar todas las ventas

    //extender el sistema agregando luego:
    //buscar ventas por fecha.
    //buscar ventas por usuario.


}
