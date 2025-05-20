package com.puntoventaema.puntoventa.mapper;

import com.puntoventaema.puntoventa.dto.ProductDTO;
import com.puntoventaema.puntoventa.model.Product;
/* importamos la clase, ahora podemos: crear objetos, acceder a sus métodos y
   usarlo para trasferir datos entre capas */


//Mapper manual
public class ProductMapper {

    // 1. de Entidad a DTO
    public static ProductDTO toDto (Product product){
            ProductDTO dto = new ProductDTO();
            dto.setId(product.getId());
            dto.setCodigoBarras(product.getCodigoBarras());
            dto.setDescripcion(product.getDescripcion());
            dto.setMarca(product.getMarca());
            dto.setPrecio(product.getPrecio());
            dto.setStock(product.getStock());
            dto.setActivo(product.getActivo());
            return  dto;
    }

    // 2. de DTO a Entidad
    public static Product toEntity(ProductDTO dto){
        Product product = new Product();
        product.setId(dto.getId()); // Esto puede omitirse en un POST
        product.setCodigoBarras(dto.getCodigoBarras());
        product.setDescripcion(dto.getDescripcion());
        product.setMarca(dto.getMarca());
        product.setPrecio(dto.getPrecio());
        product.setStock(dto.getStock());
        product.setActivo(dto.getActivo());
        return product;
    }


}
