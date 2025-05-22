package com.puntoventaema.puntoventa.controller;


import com.puntoventaema.puntoventa.dto.ProductDTO;
import com.puntoventaema.puntoventa.mapper.ProductMapper;
import com.puntoventaema.puntoventa.model.Product;
import com.puntoventaema.puntoventa.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController //Define la clase como un controlador REST. Maneja solicitudes y devuelve datos (no vistas)
@RequestMapping ("/product") //Todas las rutas de este controlador comienzan con "/product"
public class ProductController {

//1. Inyectamos la dependencia
    @Autowired
    private ProductService productService;

//2. Definimos los endpoints HTTP del controlador (GET).

    /*endpint: Es una URL o ruta que puede ser accedida desde el exterior. */

    //a. mostrar lista de productos
    @GetMapping
    public ResponseEntity<List<Product>> getProducts(){
        return ResponseEntity.ok(productService.findAll()); //respuesta HTTP
    }

    //b. buscar producto por id
    @GetMapping("/id/{id}")
    public ResponseEntity<?> getProduct(@PathVariable Long id){
        Product product = productService.findById(id);
        if(product == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado con ID: "+ id); //respuesta HTTP 404
        }
        return ResponseEntity.ok(product); //respuesta HTTP
    }


    //c. buscar producto por código de barras.
    @GetMapping("/codigo/{cb}")
    public ResponseEntity<?> getProductByCb(@PathVariable String cb){
        try{
            //Controller --> llama a "service".
            Product product = productService.findByCodigoBarras(cb);
            //Service devuelve producto --> Controller lo devuelve con HTTP 200 OK.
            return  ResponseEntity.ok(product);

        }catch(NoSuchElementException e){
            //Service no encuentra el producto --> lanza una excepción.
            //Controller captura excepción --> responde con HTTP 404 Not Found.
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado con codigo de barras: "+ cb);
        }

    }


    //d. buscar producto por marca
    @GetMapping("/marca/{marca}")
    public ResponseEntity<?> getProductByMarca(@PathVariable String marca){
        try {
            List<Product> products = productService.findByMarca(marca);
            return ResponseEntity.ok(products); // HTTP 200 OK

        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontraron productos con marca: " + marca); // HTTP 404
        }
    }

    //e. mostrar lista de productos inactivos
    @GetMapping("/inactivos")
    public ResponseEntity<?> getInactivos(){
        List<Product> inactivos= productService.findByActivoFalse();

        if(inactivos.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No hay productos inactivos");
        }
        return ResponseEntity.ok(inactivos);
    }

//2. Definimos los endpoints HTTP del controlador (POST)

    //a. Guardamos un producto
    @PostMapping // "/producto" es un endpoint para crear un producto
    public ResponseEntity<?> saveProduct(@RequestBody ProductDTO productDto){
        try{
            //Convierte el DTO a Entidad
            Product product = ProductMapper.toEntity(productDto); //retorna un Product

            //Guarda el Producto
            Product saveProduct = productService.save(product);

            //Convertir la Entidad guardada a DTO y devolver
            ProductDTO saveDto = ProductMapper.toDto(saveProduct);

            return ResponseEntity.status(HttpStatus.CREATED).body(saveDto);

        }catch(IllegalArgumentException e){

            return ResponseEntity
                    .badRequest()
                    .body("Ya existe un producto con ese código de barras"); // HTTP 400 si, por ej si ya existe el código de barras
        }
    }

//3. Definimos los endpoints HTPP del controlador (PUT)


    @PutMapping("/{id}") // {id} en la URL para identificar el producto a modificar
    public ResponseEntity<?> putProduct(@PathVariable Long id, @RequestBody ProductDTO productDto) {
        try{
            // Buscar el producto existente
            Product existingProduct = productService.findById(id);
            if (existingProduct == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Producto no encontrado con ID: " + id);
            }

            // Validar código de barras (opcional)
            if (productDto.getCodigoBarras() == null || productDto.getCodigoBarras().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("El código de barras no puede estar vacío.");
            }

            // Actualizar campos desde el DTO
            existingProduct.setCodigoBarras(productDto.getCodigoBarras());
            existingProduct.setDescripcion(productDto.getDescripcion());
            existingProduct.setMarca(productDto.getMarca());
            existingProduct.setPrecio(productDto.getPrecio());
            existingProduct.setStock(productDto.getStock());
            existingProduct.setActivo(productDto.getActivo());

            // Guardar y devolver como DTO
            Product saved = productService.save(existingProduct);
            ProductDTO savedDto = ProductMapper.toDto(saved);

            return ResponseEntity.ok(savedDto);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }


    @PatchMapping("/{id}")
    public ResponseEntity<?> patchProduct(@PathVariable Long id, @RequestBody ProductDTO productDto) {
        // Buscar el producto en la base de datos por ID
        Product existingProduct = productService.findById(id);
        if (existingProduct == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Producto no encontrado con ID: " + id);
        }

        // Actualizar los campos solo si no son nulos
        if (productDto.getCodigoBarras() != null) {
            existingProduct.setCodigoBarras(productDto.getCodigoBarras());
        }

        if (productDto.getDescripcion() != null) {
            existingProduct.setDescripcion(productDto.getDescripcion());
        }

        if (productDto.getMarca() != null) {
            existingProduct.setMarca(productDto.getMarca());
        }

        if (productDto.getPrecio() != null) {
            existingProduct.setPrecio(productDto.getPrecio());
        }

        if (productDto.getStock() != null) {
            existingProduct.setStock(productDto.getStock());
        }

        if (productDto.getActivo() != null) {
            existingProduct.setActivo(productDto.getActivo());
        }

        // Guardar el producto actualizado
        Product updatedProduct = productService.save(existingProduct);
        ProductDTO updatedDto  = ProductMapper.toDto(updatedProduct);
        return ResponseEntity.ok(updatedDto );

    }

    //baja lógica
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        try {
            Product product = productService.findById(id);
            if(product == null || !product.getActivo()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Producto no encontrado con el ID: " + id);
            }

            productService.delete(id);
            return ResponseEntity.ok("Producto dado de baja (lógica) con el ID: " + id);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar el producto con ID: " + id);
        }
    }

}
