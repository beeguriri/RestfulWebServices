package wendy.study.restfulwebservices.bean;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ResponseUserList {
    private int count;
    private List<User> users;
}
