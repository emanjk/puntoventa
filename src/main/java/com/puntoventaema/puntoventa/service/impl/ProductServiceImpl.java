package com.puntoventaema.puntoventa.service.impl;

import com.puntoventaema.puntoventa.model.Product;
import com.puntoventaema.puntoventa.repository.ProductoRepository;
import com.puntoventaema.puntoventa.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


@Service //anotación que le indica a spring que es un Bean y que debe almacenarlo en su contenedor de IoC.
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductoRepository productoRepository;
    /* Spring no busca una implementación, Spring inyecta la implementación automáticamente
     de los métodos CRUD a partir de la interfaz ProductoRepository. Si en controller.*/


    //verifica si existe un producto con el mismo código de barras y tiene ID distinto
    //al que estamos buscando con el código de barras.
    @Override
    public Product save(Product product) {
        Product existente = productoRepository.findByCodigoBarras(product.getCodigoBarras());

        if (existente != null && !existente.getId().equals(product.getId())) {
            throw new IllegalArgumentException("El código de barras ya existe");
        }

        return productoRepository.save(product);
    }

    @Override
    public Product findById(Long id) {
        return productoRepository.findById(id)
                .filter(p -> Boolean.TRUE.equals(p.getActivo())) // solo si está activo
                .orElse(null);
    }

    @Override
    public void delete(Long id){

        Product product = productoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Producto no encontrado con el ID: " + id));

        product.setActivo(false); // baja lógica
        productoRepository.save(product);
    }

    @Override
    public List<Product> findAll(){
        return productoRepository.findByActivoTrue();
    }

    @Override
    public List<Product> findByActivoFalse() {
        return productoRepository.findByActivoFalse(); //nunca retorna null
    }


    @Override
    public Product findByCodigoBarras(String codigoBarras){
        Product product = productoRepository.findByCodigoBarras(codigoBarras);
        if (product == null) {
            throw new NoSuchElementException("Producto no encontrado con código de barras: " + codigoBarras);
        }
        return product;
    }

    @Override
    public List<Product> findByMarca(String marca) {
        List<Product> products = productoRepository.findByMarca(marca);
        if (products == null || products.isEmpty()) {
            throw new NoSuchElementException("No se encontraron productos con marca: " + marca); //forma de documentar o registrar lo que ocurrió,
        }
        return products;
    }




}

