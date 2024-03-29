package wendy.study.restfulwebservices.controller;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wendy.study.restfulwebservices.bean.AdminUser;
import wendy.study.restfulwebservices.bean.AdminUserV2;
import wendy.study.restfulwebservices.bean.User;
import wendy.study.restfulwebservices.dao.UserDaoService;
import wendy.study.restfulwebservices.exception.UserNotFoundException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminUserController {

    private final UserDaoService service;

    @GetMapping("/users")
    public MappingJacksonValue retrieveAllUsersForAdmin() {

        List<User> users = service.findAll();

        List<AdminUser> adminUsers = new ArrayList<>();
        AdminUser adminUser = null;
        for(User user : users) {
            adminUser = new AdminUser();
            BeanUtils.copyProperties(user, adminUser);
            adminUsers.add(adminUser);
        }

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "ssn");
        SimpleFilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);


        MappingJacksonValue mapping = new MappingJacksonValue(adminUsers);
        mapping.setFilters(filters);

        return mapping;
    }

    /**
     * 방법1. URI로 버전관리 하기
     */
    @GetMapping("/v1/users/{id}")
    public MappingJacksonValue retrieveUserForAdmin(@PathVariable int id){

        User user = service.findOne(id);

        AdminUser adminUser = new AdminUser();

        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        } else {
            //같은 이름의 property들이 복사 됨
            BeanUtils.copyProperties(user, adminUser);
        }

        //jsonfilter 적용
        //filter provider 형태로 변형하여 사용할 수 있음
        //entity에서 설정해준 jsonfilter 이름과 동일하게 지정
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "ssn");
        SimpleFilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);


        MappingJacksonValue mapping = new MappingJacksonValue(adminUser);
        mapping.setFilters(filters);

        return mapping;
    }

    @GetMapping("/v2/users/{id}")
    public MappingJacksonValue retrieveUserForAdminV2(@PathVariable int id){

        User user = service.findOne(id);

        AdminUserV2 adminUser = new AdminUserV2();

        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        } else {
            //같은 이름의 property들이 복사 됨
            BeanUtils.copyProperties(user, adminUser);
            adminUser.setGrade("VIP"); //새로운 필드 추가
        }

        //jsonfilter 적용
        //filter provider 형태로 변형하여 사용할 수 있음
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "grade");
        SimpleFilterProvider filters = new SimpleFilterProvider().addFilter("UserInfoV2", filter);


        MappingJacksonValue mapping = new MappingJacksonValue(adminUser);
        mapping.setFilters(filters);

        return mapping;
    }

    /**
     * 방법2. request parameter로 버전 관리 하기
     * 호출할 떄 : http://localhost:8088/admin/users/1?version=1
     */
    @GetMapping(value = "/users/{id}", params = "version=1")
    public MappingJacksonValue retrieveUserForAdminV3(@PathVariable int id){

        User user = service.findOne(id);

        AdminUser adminUser = new AdminUser();

        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        } else {
            //같은 이름의 property들이 복사 됨
            BeanUtils.copyProperties(user, adminUser);
        }

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "ssn");
        SimpleFilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);


        MappingJacksonValue mapping = new MappingJacksonValue(adminUser);
        mapping.setFilters(filters);

        return mapping;
    }

    @GetMapping(value = "/users/{id}", params = "version=2")
    public MappingJacksonValue retrieveUserForAdminV4(@PathVariable int id){

        User user = service.findOne(id);

        AdminUserV2 adminUser = new AdminUserV2();

        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        } else {
            //같은 이름의 property들이 복사 됨
            BeanUtils.copyProperties(user, adminUser);
            adminUser.setGrade("VIP"); //새로운 필드 추가
        }

        //jsonfilter 적용
        //filter provider 형태로 변형하여 사용할 수 있음
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "grade");
        SimpleFilterProvider filters = new SimpleFilterProvider().addFilter("UserInfoV2", filter);


        MappingJacksonValue mapping = new MappingJacksonValue(adminUser);
        mapping.setFilters(filters);

        return mapping;
    }

    /**
     * 방법3. header 값을 이용하여 호출하기
     * 호출할 떄 :
     * http://localhost:8088/admin/users/1 의 헤더값에 X-API-VERSION : 1 추가하기
     */
    @GetMapping(value = "/users/{id}", headers = "X-API-VERSION=1")
    public MappingJacksonValue retrieveUserForAdminV5(@PathVariable int id){

        User user = service.findOne(id);

        AdminUser adminUser = new AdminUser();

        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        } else {
            //같은 이름의 property들이 복사 됨
            BeanUtils.copyProperties(user, adminUser);
        }

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "ssn");
        SimpleFilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);


        MappingJacksonValue mapping = new MappingJacksonValue(adminUser);
        mapping.setFilters(filters);

        return mapping;
    }

    @GetMapping(value = "/users/{id}", headers = "X-API-VERSION=2")
    public MappingJacksonValue retrieveUserForAdminV6(@PathVariable int id){

        User user = service.findOne(id);

        AdminUserV2 adminUser = new AdminUserV2();

        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        } else {
            //같은 이름의 property들이 복사 됨
            BeanUtils.copyProperties(user, adminUser);
            adminUser.setGrade("VIP"); //새로운 필드 추가
        }

        //jsonfilter 적용
        //filter provider 형태로 변형하여 사용할 수 있음
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "grade");
        SimpleFilterProvider filters = new SimpleFilterProvider().addFilter("UserInfoV2", filter);


        MappingJacksonValue mapping = new MappingJacksonValue(adminUser);
        mapping.setFilters(filters);

        return mapping;
    }

    /**
     * 방법4. mime-type (media type versioning)
     * 호출할 떄 :
     * http://localhost:8088/admin/users/1 의 헤더값에
     * Accept : application/vnd.company.appv1+json
     * 추가하기
     */
    @GetMapping(value = "/users/{id}", produces = "application/vnd.company.appv1+json")
    public MappingJacksonValue retrieveUserForAdminV7(@PathVariable int id){

        User user = service.findOne(id);

        AdminUser adminUser = new AdminUser();

        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        } else {
            //같은 이름의 property들이 복사 됨
            BeanUtils.copyProperties(user, adminUser);
        }

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "ssn");
        SimpleFilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);


        MappingJacksonValue mapping = new MappingJacksonValue(adminUser);
        mapping.setFilters(filters);

        return mapping;
    }

    @GetMapping(value = "/users/{id}", produces = "application/vnd.company.appv2+json")
    public MappingJacksonValue retrieveUserForAdminV8(@PathVariable int id){

        User user = service.findOne(id);

        AdminUserV2 adminUser = new AdminUserV2();

        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        } else {
            //같은 이름의 property들이 복사 됨
            BeanUtils.copyProperties(user, adminUser);
            adminUser.setGrade("VIP"); //새로운 필드 추가
        }

        //jsonfilter 적용
        //filter provider 형태로 변형하여 사용할 수 있음
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "grade");
        SimpleFilterProvider filters = new SimpleFilterProvider().addFilter("UserInfoV2", filter);


        MappingJacksonValue mapping = new MappingJacksonValue(adminUser);
        mapping.setFilters(filters);

        return mapping;
    }
}
