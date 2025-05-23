package com.puntoventaema.puntoventa.mapper;


import com.puntoventaema.puntoventa.dto.UserCreateDTO;
import com.puntoventaema.puntoventa.dto.UserDTO;
import com.puntoventaema.puntoventa.dto.UserPatchDTO;
import com.puntoventaema.puntoventa.dto.UserUpdateDTO;
import com.puntoventaema.puntoventa.model.User;

//Creamos un Mapper manualmente
public class UserMapper {

    // 1. De Entidad a DTO para mostrar
    public static UserDTO toDto(User user){
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setNombreUsuario(user.getNombreUsuario());
        dto.setRol(user.getRol());
        dto.setActivo(user.getActivo());
        return dto;
    }

    // 2. De DTO completo a Entidad (por ejemplo en una edición con PUT o PATCH)
    public static User toEntity(UserDTO dto){
        User user = new User();

        user.setId(dto.getId()); // id
        user.setNombreUsuario(dto.getNombreUsuario()); // nombreUsuario
        user.setRol(dto.getRol()); // rol
        user.setActivo(dto.getActivo()); // activo
        return user;
    }

    // 3. De DTO para crear a Entidad (sin id ni rol)
    public static User toEntity(UserCreateDTO dto) {
        User user = new User();
        user.setNombreUsuario(dto.getNombreUsuario());
        user.setClave(dto.getClave());
        user.setRol(dto.getRol());
        user.setActivo(dto.getActivo());
        return user;
    }

    //De DTO para actualizar a Entidad (PUT)
    public static void updateEntity(User user, UserUpdateDTO dto){
        //Este metodo **no crea un nuevo User**, sino que actualiza uno existente
        user.setNombreUsuario(dto.getNombreUsuario());
        user.setClave(dto.getClave());
        user.setRol(dto.getRol());
        user.setActivo(dto.getActivo());
    }

    // 5. De DTO para PATCH (actualización parcial) a Entidad
    public static void patchEntity(User user, UserPatchDTO dto) {

        if (dto.getNombreUsuario() != null) {
            user.setNombreUsuario(dto.getNombreUsuario());
        }
        if (dto.getClave() != null) {
            user.setClave(dto.getClave());
        }
        if (dto.getRol() != null) {
            user.setRol(dto.getRol());
        }
        if (dto.getActivo() != null) {
            user.setActivo(dto.getActivo());
        }
    }
}
