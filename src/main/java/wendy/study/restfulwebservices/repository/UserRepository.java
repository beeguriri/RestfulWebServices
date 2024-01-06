package wendy.study.restfulwebservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wendy.study.restfulwebservices.bean.User;

public interface UserRepository extends JpaRepository<User, Integer> {
}
