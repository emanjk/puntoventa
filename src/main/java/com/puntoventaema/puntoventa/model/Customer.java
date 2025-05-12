package com.puntoventaema.puntoventa.model;

import jakarta.persistence.Entity; //paquete=jakarta, persistence contiene clases e interfaces. Necesarios para trabajar con JPA.
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Customer {
    //Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate fecha;
    private Integer cantidadCompras;
    private Boolean activo = true;// campo para alta o baja logica.


    //Constructor vacio
    public Customer(){}

    //Getters
    public Long getId() {
        return id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Integer getCantidadCompras() {
        return cantidadCompras;
    }

    public Boolean getActivo() {
        return activo;
    }

    //Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setCantidadCompras(Integer cantidadCompras) {
        this.cantidadCompras = cantidadCompras;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
