package me.dio.controller;

import jakarta.persistence.GeneratedValue;
import me.dio.domain.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import me.dio.service.UserService;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PostMapping("/{id}")
    public ResponseEntity<User> create(@RequestBody User user) { // Requisita os dados do usuário para criação do mesmo

        // nessa linha, o método create da classe UserService é chamado para criar o usuário
        var userCreated = userService.create(user);

        // nessa linha, é criado um URI para o usuário criado, que é retornado no corpo da resposta
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(userCreated.getId())
                .toUri();

        // retorna a resposta com o status 201 Created e o corpo contendo o usuário criado
        return ResponseEntity.created(location).body(userCreated);
    }
}
