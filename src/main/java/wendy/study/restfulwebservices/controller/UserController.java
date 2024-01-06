package wendy.study.restfulwebservices.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "user-controller", description = "일반 사용자 서비스를 위한 컨트롤러") //클래스에 대한 설명을 달 때
public class UserController {

    private final UserDaoService service;

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return service.findAll();
    }

    @Operation(summary = "사용자 정보 조회 API", description = "사용자 ID를 이용해서 사용자 상세 정보를 조회 합니다.")
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "404", description = "User Not Found"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            }
    )
    @GetMapping("/users/{id}")
    public EntityModel<User> retrieveUser(
            @Parameter(description = "사용자 ID", required = true, example = "1") @PathVariable int id){

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
