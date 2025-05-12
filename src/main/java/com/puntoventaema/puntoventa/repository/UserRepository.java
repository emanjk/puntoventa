package com.puntoventaema.puntoventa.repository;


import com.puntoventaema.puntoventa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {

    User findByNombreUsuario(String nombreUsuario);

    //Con "JpaRepository<User, Long>" tenemos todos los métodos CRUD.


}
