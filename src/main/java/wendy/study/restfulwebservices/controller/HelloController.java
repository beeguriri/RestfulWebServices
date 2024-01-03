package wendy.study.restfulwebservices.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import wendy.study.restfulwebservices.bean.HelloBean;

import java.util.Locale;

@RestController
@Slf4j
@RequiredArgsConstructor
public class HelloController {

    private final MessageSource messageSource;

    @GetMapping("/hello")
    public String hello(){
        return "hello-world";
    }

    @GetMapping("/hello-bean")
    public HelloBean helloBean() {
        //json 형태로 반환
        return new HelloBean("Hello World!");
    }

    @GetMapping("/hello-bean/path-variable/{name}")
    public HelloBean helloBean(@PathVariable String name){
        //build를 intelliJ로 하면 오류가 발생
        //그때는 @PathVariable("name") 이라고 명시적으로 써주면 됨
        //gradle로 build 할때는 상관없음
        return new HelloBean(String.format("Hello, %s!", name));
    }

    @GetMapping("/hello-internationalized")
    public String helloBeanInternationalized(@RequestHeader(name="Accept-Language", required = false) Locale locale){
        log.info("msg = {} locale = {}", messageSource.getMessage("greeting.message", null, locale), locale);
        return messageSource.getMessage("greeting.message", null, locale);
    }
}
