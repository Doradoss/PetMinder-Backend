package com.upc.petminder.controllers;

import com.upc.petminder.dtos.HistorialMedicoDTO.HistorialMedicoDto;
import com.upc.petminder.dtos.UserDTO.UserDto;
import com.upc.petminder.serviceinterfaces.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class UserController {
    final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/findall-users")
    public ResponseEntity<List<UserDto>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping("/registrar-user")
    public ResponseEntity<UserDto> create(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(userService.save(userDto), HttpStatus.CREATED);
    }

    // Actualizar un usuario
    @PutMapping("/actualizar-user/{id}")
    public ResponseEntity<UserDto> update(@PathVariable Long id, @RequestBody UserDto userDto) {
        // Llamar al service para actualizar el usuario
        UserDto updatedUserDto = userService.update(id, userDto);

        return ResponseEntity.ok(updatedUserDto);
    }

    @DeleteMapping("/eliminar-user/{id}")
    public void delete(@PathVariable("id") Long id){
        userService.delete(id);
    }
}
