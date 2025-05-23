package com.puntoventaema.puntoventa.controller;


import com.puntoventaema.puntoventa.dto.UserCreateDTO;
import com.puntoventaema.puntoventa.dto.UserDTO;
import com.puntoventaema.puntoventa.dto.UserPatchDTO;
import com.puntoventaema.puntoventa.dto.UserUpdateDTO;
import com.puntoventaema.puntoventa.mapper.UserMapper;
import com.puntoventaema.puntoventa.model.User;
import com.puntoventaema.puntoventa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.header.Header;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.RecursiveTask;

@RestController
@RequestMapping("/user")
public class UserController {

    // 1. INYECCIÓN DE DEPENDENCIA
    @Autowired
    private UserService userService;

    // 2. Métodos GET
    @GetMapping
    public ResponseEntity<List<UserDTO>> getUser(){
        List<User> users = userService.findAll();
        List<UserDTO> userDtos = users.stream()
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
        UserDTO userDto = UserMapper.toDto(user);
        return  ResponseEntity.ok(userDto);
    }

    //usuario por nombre usuario
    @GetMapping("/name/{name}")
    public ResponseEntity<?> getUserByName(@PathVariable String name){
        try{
            User user = userService.findByNombreUsuario(name); //con JPA nos devuelve un User o null.
            UserDTO userDto = UserMapper.toDto(user);
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
        List<UserDTO> usersDtos = users.stream().map(UserMapper ::toDto).toList();

        return ResponseEntity.ok(usersDtos);

    }

    //3. Metodo POST
    @PostMapping
    public ResponseEntity<?> saveProduct(@RequestBody UserCreateDTO userDto){
        try{
            //convertimos el DTO a Entidad
            User user = UserMapper.toEntity(userDto);

            //guardamos el usuario
            User saveUser = userService.save(user);

            //devolvemos el DTO como respuesta
            UserDTO responseDto = UserMapper.toDto(saveUser);

            return  ResponseEntity.status(HttpStatus.CREATED).body(responseDto);


        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    //4. Metodo Put (Luego con Spring Security evitamos cambiar él: rol y la clave)
    @PutMapping("/{id}")
    public ResponseEntity<?> putUser(@PathVariable Long id, @RequestBody UserUpdateDTO userDto){
        User existingUser = userService.findById(id);

        if (existingUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontró el usuario con id: " + id);
        }

        if (userDto.getNombreUsuario() == null || userDto.getNombreUsuario().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El nombre de usuario es obligatorio");
        }

        if (userDto.getClave() == null || userDto.getClave().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Ingrese una contraseña");
        }

        // usamos mapper para actualizar el usuario existente
        UserMapper.updateEntity(existingUser, userDto);

        // guardamos los cambios
        User saved = userService.save(existingUser);

        // Devolvés un DTO como respuesta
        UserDTO responseDto = UserMapper.toDto(saved);
        return ResponseEntity.ok(responseDto);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<?> patchUser(@PathVariable Long id, @RequestBody UserPatchDTO dto){

        User existingUser = userService.findById(id);
        if(existingUser == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User no encontrado con el id: " + id);
        }

        //Aplicamos cambios parciales
        UserMapper.patchEntity(existingUser, dto);

        User saved = userService.save(existingUser); //guardamos en la bds a la Entidad
        UserDTO responseDto = UserMapper.toDto(saved);
        return ResponseEntity.ok(responseDto);

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        try{
            userService.delete(id); // Lógica de borrado
            return  ResponseEntity.status(HttpStatus.NO_CONTENT).build();// 204 OK sin cuerpo

        }catch (NoSuchElementException e){

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontro el usuario con id: "+ id); // 204 OK sin cuerpo
        }
    }


}
