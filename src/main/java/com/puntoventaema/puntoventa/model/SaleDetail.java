package com.puntoventaema.puntoventa.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class SaleDetail {

    //Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Sale venta; //(relación con Venta)
    @ManyToOne
    private Product producto; //(relación con Producto)
    private Integer cantdad;
    private BigDecimal precioUnitario;

    //Constructor vacio (requerido por JPA)
    public SaleDetail(){};

    //Getters
    public Long getId() {
        return id;
    }

    public Sale getVenta() {
        return venta;
    }

    public Product getProducto() {
        return producto;
    }

    public Integer getCantdad() {
        return cantdad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    //Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setVenta(Sale venta) {
        this.venta = venta;
    }

    public void setProducto(Product producto) {
        this.producto = producto;
    }

    public void setCantdad(Integer cantdad) {
        this.cantdad = cantdad;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }
}
