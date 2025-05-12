package com.puntoventaema.puntoventa.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;


@Entity //marca la clase como una Entidad JPA
public class Sale {

    //Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime fechaHora;
    private Double montoTotal;
    private String usuario; //cajero que hizo la venta



    //Constructor vacio (Requerido por JPA)
    public  Sale(){}

    //Getters
    public Long getId() {
        return id;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public Double getMontoTotal() {
        return montoTotal;
    }

    public String getUsuario() {
        return usuario;
    }



    //Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public void setMontoTotal(Double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }


}
