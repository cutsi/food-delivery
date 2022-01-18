package cut.food.fooddelivery.repos;

import cut.food.fooddelivery.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;
@Repository
@Transactional
public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByName(String firstName);
    Optional<User> findByPhone(String phone);
    Optional<User> findByAddress(String address);
}
