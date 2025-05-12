
package com.puntoventaema.puntoventa.repository;
import com.puntoventaema.puntoventa.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//"Product" es la entidad que este repositorio va a manejar.
//"Long" es el tipo de ID (clave primaria) de Product.

@Repository
public interface ProductoRepository extends JpaRepository<Product, Long>{
//creando un repositorio de acceso a datos para la entidad Product

    //findAll()
    //findById(Long id)
    //save(Product producto)
    //deleteById(Long id)
    //etc...

    //Métodos personalizados:
    Product findByCodigoBarras(String codigoBarras);
    List<Product> findByMarca (String marca);
    List<Product> findByActivoTrue();
    List<Product> findByActivoFalse();

}
