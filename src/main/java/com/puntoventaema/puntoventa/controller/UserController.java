package com.puntoventaema.puntoventa.controller;


import com.puntoventaema.puntoventa.dto.UserResponseDTO;
import com.puntoventaema.puntoventa.mapper.UserMapper;
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
    public ResponseEntity<List<UserResponseDTO>> getUser(){
        List<User> users = userService.findAll();
        List<UserResponseDTO> userDtos = users.stream()
                .map(UserMapper ::toDto)
                .toList();

        return ResponseEntity.ok(userDtos); //200 OK

    }

    //usuario por id
    @GetMapping("/id/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id){
        User user = userService.findById(id); //con JPA nos devuelve un User o null
        if(user == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado con el id: "+ id);
        }
        UserResponseDTO userDto = UserMapper.toDto(user);
        return  ResponseEntity.ok(userDto);
    }

    //usuario por nombre usuario
    @GetMapping("/name/{name}")
    public ResponseEntity<?> getUserByName(@PathVariable String name){
        try{
            User user = userService.findByNombreUsuario(name); //con JPA nos devuelve un User o null.
            UserResponseDTO userDto = UserMapper.toDto(user);
            return ResponseEntity.ok(userDto);

        }catch(NoSuchElementException e){

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontro el usario: "+ name);
        }
    }


    //usuarios por activos o inactivos
    @GetMapping("/activos/{activos}")
    public ResponseEntity<?> getUserByActivos(@PathVariable Boolean activos){
        List<User> users = userService.findByActivos(activos); //con JPA nos devuelve una lista vacía si no hay coincidencias.
        List<UserResponseDTO> usersDtos = users.stream().map(UserMapper ::toDto).toList();

        return ResponseEntity.ok(usersDtos);

    }



    //3. Metodo POST
    @PostMapping
    public ResponseEntity<?> saveProduct(@RequestBody UserResponseDTO userDto){
        try{
            //convertimos el DTO a Entidad
            User user = UserMapper.toEntity(userDto);

            //guardamos el usuario
            User saveUser = userService.save(user);

            //devolvemos el DTO como respuesta
            UserResponseDTO responseDto = UserMapper.toDto(saveUser);

            return  ResponseEntity.ok(responseDto);


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
