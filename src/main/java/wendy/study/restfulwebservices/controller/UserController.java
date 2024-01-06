package wendy.study.restfulwebservices.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import wendy.study.restfulwebservices.bean.User;
import wendy.study.restfulwebservices.dao.UserDaoService;
import wendy.study.restfulwebservices.exception.UserNotFoundException;

import java.net.URI;
import java.util.List;
import java.util.Objects;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserDaoService service;

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return service.findAll();
    }

    @GetMapping("/users/{id}")
    public EntityModel<User> retrieveUser(@PathVariable int id){

        User user = service.findOne(id);

        if (user == null) {
            //Status : 404 Not Found
            //"message": "ID[4] not found",
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        EntityModel<User> entityModel = EntityModel.of(user);

        //user객체에 link 기능 추가
        //static method로 import
        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(link.withRel("all-users")); //all-users -> http://localhost:8088/users

        return entityModel;
    }

    //Status : 201 Created
    //Headers => location : http://localhost:8088/users/4
    @PostMapping("/users")
    public ResponseEntity<User> addUser(@Valid @RequestBody User user) {

        User savedUser = service.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Objects> deleteUser(@PathVariable int id) {

        User deleteUser = service.deleteById(id);

        if(deleteUser == null)
            throw new UserNotFoundException(String.format("ID[%s] not found", id));

        return ResponseEntity.noContent().build();
    }
}
