package wendy.study.restfulwebservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wendy.study.restfulwebservices.bean.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {
}
