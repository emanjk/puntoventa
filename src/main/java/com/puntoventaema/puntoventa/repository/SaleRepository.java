package com.puntoventaema.puntoventa.repository;


import com.puntoventaema.puntoventa.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository //Es una anotación de Spring que indica que esta interfaz es un componente de acceso a datos.
public interface SaleRepository extends JpaRepository<Sale,Long> {



}
