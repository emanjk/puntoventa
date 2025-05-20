package com.puntoventaema.puntoventa.controller;


import com.puntoventaema.puntoventa.model.User;
import com.puntoventaema.puntoventa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/user")
public class UserController {

    // 1. INYECCIÓN DE DEPENDENCIA
    @Autowired
    private UserService userService;

    // 2. Métodos GET
    @GetMapping
    public ResponseEntity<List<User>> getUser(){
        return ResponseEntity.ok(userService.findAll());
    }

    //usuario por id
    @GetMapping("/id/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id){
        User user = userService.findById(id);
        if(user == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado con el id: "+ id);
        }
        return ResponseEntity.ok(user);
    }

    //usuario por nombre usuario
    @GetMapping("/name/{name}")
    public ResponseEntity<?> getUserByName(@PathVariable String name){
        try{
            User userName = userService.findByNombreUsuario(name);
            return  ResponseEntity.ok(userName);

        }catch(NoSuchElementException e){

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontro el usario: "+ name);
        }
    }


    //usuarios por activos o inactivos
    @GetMapping("/activos/{activos}")
    public ResponseEntity<?> getUserByActivos(@RequestParam Boolean estado){
        List<User> users = userService.findByActivos(estado);
        return ResponseEntity.ok(users);

    }

    //3. Metodo POST
    @PostMapping
    public ResponseEntity<?> saveProduct(@RequestBody User user){
        try{
            User saveUser = userService.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(saveUser); //HTTP 201

        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }



    }

    //4. Metodo Put (Luego con Spring Security evitamos cambiar él: rol y la clave)
    @PutMapping("/{id}")
    public ResponseEntity<?> putUser(@PathVariable Long id, @RequestBody User user){
        User existingUser = userService.findById(id);
        if(existingUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontró el usuario con id: " + id);
        }
        if(user.getNombreUsuario()==null || user.getNombreUsuario().isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El nombre de usuario es obligatorio ");
        }
        if(user.getClave()==null || user.getClave().isEmpty()){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ingrese una contraseña");
        }

        //actualizamos los campos del usuario
        existingUser.setActivo(user.getActivo());
        existingUser.setClave(user.getClave());
        existingUser.setRol(user.getRol());
        existingUser.setNombreUsuario(user.getNombreUsuario());

        //guardamos y devolvemos el producto actializado
        User saved = userService.save(existingUser);
        return  ResponseEntity.ok(saved);



    }



}
