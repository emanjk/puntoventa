package com.puntoventaema.puntoventa.service;

import com.puntoventaema.puntoventa.model.User;

import java.util.List;

public interface UserService {

    //métodos personalizados abstractos(sin implementación)

    User save(User user); //guardamos un usuario (alt: empleado)
    User findById(Long id); //encontrar usuario por id
    void delete(Long id); //eliminar usuario por id
    List<User> findAll(); //listar todos los usuarios
    User findByNombreUsuario(String nombreUsuario); //buscar usuario por su nombre

    //métodos por defecto de JPA son: save, findById(), deleteById(), etc..
}
