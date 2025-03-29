package app.user.repository;

import app.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @Query(
            "select u from User u where u.id = :userUuid"
            //                          ^^^^ because field is UUID User.id for column users.user_uuid
    )
    Optional<User> findByUuid(UUID userUuid);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByShownUsername(String shownUsername);

    @Query(
            "select u from User u where u.username = :username or u.email = :email"
    )
    Optional<User> findByUsernameOrEmail(String username, String email);

}
