package com.upc.petminder.dtos.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private Boolean enabled;
    private String nombre;
    private String apellidos;
    private String celular;
    private String email;
}
