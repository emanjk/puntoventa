package com.puntoventaema.puntoventa.mapper;


import com.puntoventaema.puntoventa.dto.UserResponseDTO;
import com.puntoventaema.puntoventa.model.User;

//Creamos un Mapper manualmente
public class UserMapper {

    // 1. De Entidad a DTO
    public static UserResponseDTO toDto(User user){
        UserResponseDTO dto = new UserResponseDTO();

        dto.setId(user.getId());
        dto.setNombreUsuario(user.getNombreUsuario());
        dto.setRol(user.getRol());
        dto.setActivo(user.getActivo());
        return dto;
    }

    // 2. De DTO a Entidad
    public static User toEntity(UserResponseDTO dto){
        User user = new User();

        user.setId(dto.getId());
        user.setNombreUsuario(dto.getNombreUsuario());
        user.setRol(dto.getRol());
        user.setActivo(dto.getActivo());

        return user;
    }


}
