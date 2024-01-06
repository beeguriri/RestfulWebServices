package wendy.study.restfulwebservices.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(value = {"password", "ssn"})
@Schema(description = "사용자 상세 정보를 위한 도메인 객체")
public class User {

    @Schema(title = "사용자 ID", description = "사용자 ID는 자동 생성 됩니다.")
    private Integer id;

    @Schema(title = "사용자 이름", description = "사용자 이름을 입력 합니다.")
    @Size(min = 2, message = "이름은 2글자 이상 입력 해 주세요.")
    private String name;

    @Schema(title = "사용자 등록일", description = "사용자 등록일을 입력 합니다. 입력 하지 않으면 현재 날짜가 저장 됩니다.")
    @Past(message = "등록일은 미래 날짜를 입력 하실 수 없습니다.")
    private Date joinDate;

//    @JsonIgnore //외부에 노출 하고 싶지 않은 데이터에 설정
    @Schema(title = "사용자 비밀번호", description = "사용자의 비밀번호를 입력 합니다.")
    private String password;
    @Schema(title = "사용자 주민번호", description = "사용자의 주민번호를 입력 합니다.")
    private String ssn;

}
