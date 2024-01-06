package wendy.study.restfulwebservices.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wendy.study.restfulwebservices.bean.User;
import wendy.study.restfulwebservices.exception.UserNotFoundException;
import wendy.study.restfulwebservices.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/jpa")
@RequiredArgsConstructor
public class UserJPAController {

    private final UserRepository userRepository;

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public EntityModel<User> retrieveUsers(@PathVariable("id") int id) {
        Optional<User> user = userRepository.findById(id);

        if(user.isEmpty())
            throw new UserNotFoundException("id-" + id);

        EntityModel<User> entityModel = EntityModel.of(user.get());
        //user객체에 link 기능 추가
        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(link.withRel("all-users")); //all-users -> http://localhost:8088/users

        return entityModel;
    }
}
