package com.puntoventaema.puntoventa.model;


import jakarta.persistence.*;

//representa una tabla en nuestra BDS
@Entity //marca la clase como una entidad JPA (Java Persistence API)
public class Product {

    //Atributos:
    @Id // indica el campo clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generación automática del ID (autoincremental)
    private Long id;

    @Column(nullable = false)
    private String codigoBarras; // Se indica que este campo no puede ser nulo
    private String descripcion;
    private String marca;
    private Double precio;
    private Integer stock;
    @Column (nullable = false)  // Se indica que este campo no puede ser nulo
    private Boolean activo = true; //alta o baga logica


    //Constructor vacío (requerido por JPA)
    public Product(){

    }

    //Getters
    public Long getId() {
        return id;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getMarca() {
        return marca;
    }

    public Double getPrecio() {
        return precio;
    }

    public Integer getStock() {
        return stock;
    }

    public Boolean getActivo() {
        return activo;
    }

    //Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
