package com.upc.petminder.serviceinterfaces;

import com.upc.petminder.dtos.UserDTO.UserDto;
import com.upc.petminder.entities.Users;
import com.upc.petminder.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private List<Users> users = new ArrayList<>();  // Inicializa la lista de usuarios
    final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Lista todos los usuarios
    public List<UserDto> findAll() {
        List<Users> users = userRepository.findAll();
        ModelMapper modelMapper = new ModelMapper();
        List<UserDto> userDtos = new ArrayList<>();
        for (Users user : users) {
            UserDto userDto = modelMapper.map(user, UserDto.class);
            userDtos.add(userDto);
        }
        return userDtos;
    }

    // Registrar un nuevo usuario
    public UserDto save(UserDto userDto) {
        ModelMapper modelMapper = new ModelMapper();
        Users user = modelMapper.map(userDto, Users.class);
        user = userRepository.save(user);
        return modelMapper.map(user, UserDto.class);
    }


    // Actualizar un usuario
    public UserDto update(Long id, UserDto userDto) {
        // Buscar el usuario existente
        Users existingUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        // Mapear los nuevos datos del DTO al usuario existente
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(userDto, existingUser);

        // Guardar el usuario actualizado
        Users updatedUser = userRepository.save(existingUser);

        // Mapear la entidad actualizada al DTO y devolverla
        UserDto updatedUserDto = modelMapper.map(updatedUser, UserDto.class);
        return updatedUserDto;
    }


    // Eliminar una usuario por ID
    public void delete(Long id) {
        Users users = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrada"));

        // Eliminar la mascota
        userRepository.delete(users);
    }
}
