package com.mercadolibre.meaw_people_list_control_api.controller;

import com.mercadolibre.meaw_people_list_control_api.models.User;
import com.mercadolibre.meaw_people_list_control_api.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/pessoas")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> searchUsersByTerm(@RequestParam(name = "t") String termoBusca) {
        List<User> usersFound = userService.searchUsersByTerm(termoBusca);

        if (StringUtils.isEmpty(termoBusca)) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().body(usersFound);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        try {
            UUID uuid = UUID.fromString(id);
            Optional<User> user = userService.getUserById(uuid);

            return user.map(value -> ResponseEntity.ok().body(value))
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            validarAtributos(user);

            User createdUser = userService.createUser(user);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .header("Location", "/pessoas/" + createdUser.getUuid())
                    .body("Usuário criado com sucesso");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
        }
    }

    private void validarAtributos(User user) {
        if (user.getNickname() == null||user.getNickname().isEmpty()) {
            throw new IllegalArgumentException("Nickname não pode ser nulo ou vazio");
        }
        if (user.getName() == null||user.getName().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser nulo ou vazio");
        }
        if (user.getBirthdate() == null) {
            throw new IllegalArgumentException("Data de nascimento não pode ser nula");
        }

    }


}