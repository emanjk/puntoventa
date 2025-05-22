package com.puntoventaema.puntoventa.dto;

public class ProductDTO {

    private Long id;
    private String codigoBarras;
    private String descripcion;
    private String marca;
    private Double precio;
    private Integer stock;
    private Boolean activo;

    // Constructor vacío (necesario para frameworks como Jackson)
    public ProductDTO() {
    }


    public ProductDTO(Long id, String codigoBarras, String descripcion, String marca, Double precio, Integer stock, Boolean activo) {
        this.id = id;
        this.codigoBarras = codigoBarras;
        this.descripcion = descripcion;
        this.marca = marca;
        this.precio = precio;
        this.stock = stock;
        this.activo = activo;
    }

    //Metodos Getters
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

    //Metodos Setters

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
