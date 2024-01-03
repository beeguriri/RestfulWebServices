package wendy.study.restfulwebservices.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import wendy.study.restfulwebservices.bean.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class UserDaoService {

    private static List<User> users = new ArrayList<>();
    private static int usersCount = 3;

    static {
        users.add(new User(1, "Alice", new Date()));
        users.add(new User(2, "Bob", new Date()));
        users.add(new User(3, "Carry", new Date()));
    }

    public List<User> findAll() {
        return users;
    }

    public User save(User user) {

        if (user.getId() == null)
            user.setId(++usersCount);

        if (user.getJoinDate() == null)
            user.setJoinDate(new Date());

        users.add(user);

        return user;
    }

    public User findOne(int id) {

        for (User user : users)
            if (user.getId() == id)
                return user;

        return null;
    }
}