package wendy.study.restfulwebservices.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import wendy.study.restfulwebservices.bean.User;
import wendy.study.restfulwebservices.dao.UserDaoService;
import wendy.study.restfulwebservices.exception.UserNotFoundException;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserDaoService service;

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return service.findAll();
    }

    @GetMapping("/users/{id}")
    public User retrieveUser(@PathVariable int id){

        User user = service.findOne(id);

        if (user == null) {
            //Status : 404 Not Found
            //"message": "ID[4] not found",
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        return user;
    }

    //Status : 201 Created
    //Headers => location : http://localhost:8088/users/4
    @PostMapping("/users")
    public ResponseEntity<User> addUser(@RequestBody User user) {

        User savedUser = service.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
