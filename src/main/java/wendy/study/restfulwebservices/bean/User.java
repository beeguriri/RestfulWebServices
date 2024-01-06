package wendy.study.restfulwebservices.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(value = {"password", "ssn"})
public class User {

    private Integer id;

    @Size(min = 2, message = "이름은 2글자 이상 입력 해 주세요.")
    private String name;

    @Past(message = "등록일은 미래 날짜를 입력 하실 수 없습니다.")
    private Date joinDate;

//    @JsonIgnore //외부에 노출 하고 싶지 않은 데이터에 설정
    private String password;
    private String ssn;

}
