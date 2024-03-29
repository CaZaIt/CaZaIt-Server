package shop.cazait.domain.user.repository;


import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.cazait.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User,UUID> {

    Optional<User> findByAccountName(String email);
    Optional<User> findByNickname(String nickname);

    Optional<User> findById(UUID id);

    Optional<User> findByPhoneNumber(String phoneNumber);

    Optional<User> findByKakaoId(Long kakaoId);

}
