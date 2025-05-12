package com.puntoventaema.puntoventa.service.impl;

import com.puntoventaema.puntoventa.model.Sale;
import com.puntoventaema.puntoventa.repository.SaleRepository;
import com.puntoventaema.puntoventa.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service //le dice a Spring que esta clase es un Bean de servicio.
public class SaleServiceImpl  implements SaleService {

    @Autowired
    private SaleRepository saleRepository; //acceso a la bds.

    @Override
    public Sale save(Sale sale){
        if(sale.getFechaHora()==null){
            sale.setFechaHora(LocalDateTime.now());
        }
        return saleRepository.save(sale); //guarda la venta en la bds.

    }

    @Override
    public Sale findById(Long id){
        return saleRepository.findById(id).orElse(null); //retorna la venta si la encuentra o null
    }

    @Override
    public void delete(Long id){
        if(saleRepository.existsById(id)){
            throw new NoSuchElementException("Producto no encontrado con el id: "+id);
        }
         saleRepository.deleteById(id); //elimina o da de baja por id.
    }

    @Override
    public List<Sale> findAll(){
        return saleRepository.findAll(); //devuelve una lista, cargada o vacia
    }

}
