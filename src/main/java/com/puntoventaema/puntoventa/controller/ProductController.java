package com.puntoventaema.puntoventa.controller;


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
    @GetMapping("/marca/{marca}") //"GET /producto/marca/{marca}" = endpoint
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
    public ResponseEntity<?> saveProduct(@RequestBody Product product){
        try{
            //Controller --> llama a service para guardar el producto
            Product saveProduct = productService.save(product);//la entidad se pasa entre capas (Controller --> Service --> Repository)
            return ResponseEntity.status(HttpStatus.CREATED).body(saveProduct); //HTTP 201

        }catch(IllegalArgumentException e){

            return ResponseEntity
                    .badRequest()
                    .body("Ya existe un producto con ese código de barras"); // HTTP 400 si, por ej si ya existe el código de barras
        }
    }

//3. Definimos los endpoints HTPP del controlador (PUT)

    @PutMapping("/{id}") // {id} en la URL para identificar el producto a modificar
    public ResponseEntity<?> putProduct(@PathVariable Long id, @RequestBody Product product) {
        try {
            // Verificamos si el producto existe con el ID proporcionado en la URL
            Product existingProduct = productService.findById(id);
            if (existingProduct == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Producto no encontrado con ID: " + id); // 404 si no existe
            }

            // Validación básica de código de barras (opcional)
            if (product.getCodigoBarras() == null || product.getCodigoBarras().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("El código de barras no puede estar vacío.");
            }

            // Actualizar los campos del producto (solo los proporcionados)
            existingProduct.setCodigoBarras(product.getCodigoBarras());
            existingProduct.setDescripcion(product.getDescripcion());
            existingProduct.setMarca(product.getMarca());
            existingProduct.setPrecio(product.getPrecio());
            existingProduct.setStock(product.getStock());
            existingProduct.setActivo(product.getActivo());

            // Guardamos y devolvemos el producto actualizado
            Product saved = productService.save(existingProduct);
            return ResponseEntity.ok(saved); // 200 OK con el producto actualizado

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }


    @PatchMapping("/{id}")
    public ResponseEntity<?> patchProduct(@PathVariable Long id, @RequestBody Product product) {
        // Buscar el producto en la base de datos por ID
        Product existingProduct = productService.findById(id);
        if (existingProduct == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Producto no encontrado con ID: " + id);
        }

        // Actualizar los campos solo si no son nulos
        if (product.getCodigoBarras() != null) {
            existingProduct.setCodigoBarras(product.getCodigoBarras());
        }

        if (product.getDescripcion() != null) {
            existingProduct.setDescripcion(product.getDescripcion());
        }

        if (product.getMarca() != null) {
            existingProduct.setMarca(product.getMarca());
        }

        if (product.getPrecio() != null) {
            existingProduct.setPrecio(product.getPrecio());
        }

        if (product.getStock() != null) {
            existingProduct.setStock(product.getStock());
        }

        if (product.getActivo() != null) {
            existingProduct.setActivo(product.getActivo());
        }

        // Guardar el producto actualizado
        Product updatedProduct = productService.save(existingProduct);
        return ResponseEntity.ok(updatedProduct); // Responder con el producto actualizado
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
