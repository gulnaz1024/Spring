package kg.edu.alatoo.springWeb.repos;

import kg.edu.alatoo.springWeb.modules.Book;
import kg.edu.alatoo.springWeb.modules.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    void deleteByUsername(String username);
    User save(User user);
    List<User> findAll();

    @Query("SELECT u FROM User u WHERE u.email = ?1")
    public User findByEmail(String email);

    public User findByResetPasswordToken(String token);
}