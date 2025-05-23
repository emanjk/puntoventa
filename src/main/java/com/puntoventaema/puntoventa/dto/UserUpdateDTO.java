package com.puntoventaema.puntoventa.dto;

public class UserUpdateDTO {

    private String nombreUsuario;
    private String clave;
    private String rol;
    private Boolean activo;

    public UserUpdateDTO() {
    }

    public UserUpdateDTO(String nombreUsuario, String clave, String rol, Boolean activo) {
        this.nombreUsuario = nombreUsuario;
        this.clave = clave;
        this.rol = rol;
        this.activo = activo;
    }

    //get
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getClave() {
        return clave;
    }

    public String getRol() {
        return rol;
    }

    public Boolean getActivo() {
        return activo;
    }


    //set
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
