package shop.cazait.domain.user.repository;


import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.cazait.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findByNickname(String nickname);

}
