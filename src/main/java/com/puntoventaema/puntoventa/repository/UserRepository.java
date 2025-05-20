package com.puntoventaema.puntoventa.repository;


import com.puntoventaema.puntoventa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {

    User findByNombreUsuario(String nombreUsuario);
    List<User> findByActivo(Boolean estado);

    //Con "JpaRepository<User, Long>" tenemos todos los métodos CRUD.


}
