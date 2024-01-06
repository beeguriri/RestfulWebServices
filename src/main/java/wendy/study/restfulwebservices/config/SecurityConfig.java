package wendy.study.restfulwebservices.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

    @Bean
    UserDetailsService userDetailsService() {

        //어플리케이션 실행될 때 기본으로 생성되는 유저 정보
        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();

        UserDetails newUser = User.withUsername("wendy")
                .password(passwordEncoder().encode("pass1234"))
                .authorities("read")
                .build();

        userDetailsManager.createUser(newUser);

        return userDetailsManager;
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //h2 web-console 사용하기 위한 설정
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        //인증 거치지 않고 접근 가능 하도록 허용
        return (web) -> web.ignoring()
                            .requestMatchers(new AntPathRequestMatcher("/h2-console/**"));
    }
}
