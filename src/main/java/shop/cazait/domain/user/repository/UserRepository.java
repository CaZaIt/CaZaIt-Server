package shop.cazait.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.cazait.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User,Long> {
    public User findByEmail(String email);
}
