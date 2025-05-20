package com.puntoventaema.puntoventa.service.impl;

import com.puntoventaema.puntoventa.model.User;
import com.puntoventaema.puntoventa.repository.UserRepository;
import com.puntoventaema.puntoventa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    /* Spring no busca una implementación, Spring inyecta la implementación automáticamente
       de los métodos CRUD a partir de la interfaz ProductoRepository. Si en controller. *
    */

    @Override
    public User save(User user){
        // Validar nombre de usuario
        if (user.getNombreUsuario() == null || user.getNombreUsuario().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de usuario es obligatorio");
        }
        if (user.getNombreUsuario().length() < 5) { // usá 5 o el mínimo que quieras (no 20, que es mucho)
            throw new IllegalArgumentException("El nombre de usuario debe tener al menos 5 caracteres");
        }
        if (userRepository.findByNombreUsuario(user.getNombreUsuario()) != null) {
            throw new IllegalArgumentException("El nombre de usuario ya existe");
        }

        // Validar clave
        if (user.getClave() == null || user.getClave().trim().isEmpty()) {
            throw new IllegalArgumentException("La clave es obligatoria");
        }
        if (user.getClave().length() < 6) {
            throw new IllegalArgumentException("La clave debe tener al menos 6 caracteres");
        }

        return userRepository.save(user);

    }

    @Override
    public User findById(Long id){
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id){
        //verificamos si el user existe en la bds
        if(!userRepository.existsById(id)){
            throw new NoSuchElementException("No existe el usuario con ID: " + id);
        }

        //eliminamos el usuario si existe
        userRepository.deleteById(id);
    }

    @Override
    public List<User> findAll(){

        return userRepository.findAll(); //devuelve todos los usuarios de la bds.
    }

    @Override
    public User findByNombreUsuario(String nombreUsuario){
        User user = userRepository.findByNombreUsuario(nombreUsuario);
        if(user ==null){
            throw new NoSuchElementException("Usuario no encontrado con nombre: "+nombreUsuario);
        }
        return user;
    }

    @Override
    public List<User> findByActivos(Boolean estado){
        return userRepository.findByActivo(estado);
    }





    /*Spring Data JPA: lo crea automáticamente a "findByNombreUsuario"
          gracias a una característica llamada query
          derivada por nombre de metodo.*/

    /*La "magia" está en Spring Data JPA. Usando el prefijo "findBy..."()*/

}

