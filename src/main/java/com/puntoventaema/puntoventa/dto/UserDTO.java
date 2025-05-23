package com.puntoventaema.puntoventa.dto;

//DTO para mostrar al cliente (con ID y sin Password)
public class UserDTO {

    //Atributos necesarios
    private Long id;
    private String nombreUsuario;
    private String rol;
    private Boolean activo = true;


    public UserDTO(){}

    public UserDTO(Long id, String nombreUsuario, String rol, Boolean activo) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.rol = rol;
        this.activo = activo;
    }


    //get
    public Long getId() {
        return id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getRol() {
        return rol;
    }

    public Boolean getActivo() {
        return activo;
    }

    //set
    public void setId(Long id) {
        this.id = id;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
