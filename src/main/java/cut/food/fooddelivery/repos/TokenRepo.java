package cut.food.fooddelivery.repos;

import cut.food.fooddelivery.entities.Image;
import cut.food.fooddelivery.entities.Token;
import cut.food.fooddelivery.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface TokenRepo extends JpaRepository<Token, Long> {
    Optional<Token> findTokenByUser(User user);
    Optional<Token> findTokenByToken(String token);
    Optional<Token> findTokenByInfo(String info);
    List<Token> findAllByUser(User user);

}
