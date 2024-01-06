package wendy.study.restfulwebservices.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import wendy.study.restfulwebservices.bean.Post;
import wendy.study.restfulwebservices.bean.ResponseUserList;
import wendy.study.restfulwebservices.bean.User;
import wendy.study.restfulwebservices.exception.UserNotFoundException;
import wendy.study.restfulwebservices.repository.PostRepository;
import wendy.study.restfulwebservices.repository.UserRepository;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/jpa")
@RequiredArgsConstructor
public class UserJPAController {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/users-count")
    public ResponseEntity<EntityModel<ResponseUserList>> retrieveAllUsersWithCount() {

        List<User> users = userRepository.findAll();
        ResponseUserList response = ResponseUserList.builder()
                .count(users.isEmpty() ? 0 : users.size())
                .users(users)
                .build();

        EntityModel<ResponseUserList> entityModel = EntityModel.of(response);
        //user객체에 link 기능 추가
        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(link.withSelfRel());

        return ResponseEntity.ok(entityModel);
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

    @DeleteMapping("/users/{id}")
    public void deleteUserById(@PathVariable("id") int id) {
        userRepository.deleteById(id);
        // 401Unauthorized
        // resource의 변경이 일어날 경우
        // filter chain 추가 필요
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {

        if(user.getJoinDate() == null)
            user.setJoinDate(new Date());

        User savedUser = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/users/{id}/posts")
    public List<Post> retrieveAllPostsByUser(@PathVariable("id") int id) {

        Optional<User> user = userRepository.findById(id);

        if(user.isEmpty())
            throw new UserNotFoundException("id-" + id);

        return user.get().getPosts();
    }

    @PostMapping("/users/{id}/posts")
    public ResponseEntity<Post> createPost(@PathVariable("id") int id, @RequestBody Post post) {

        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("id-"+id));
        post.setUser(user);

        postRepository.save(post);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(post.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
