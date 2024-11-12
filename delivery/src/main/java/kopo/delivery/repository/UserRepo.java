package kopo.delivery.repository;

import kopo.delivery.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer> {

    boolean existsByUserID(String userID);

    User findByUserID(String username);

    Optional<User> findByNickname(String nickname);
}
