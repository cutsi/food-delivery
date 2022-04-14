package cut.food.fooddelivery.repos;

import cut.food.fooddelivery.entities.SendMessageToUs;
import cut.food.fooddelivery.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SendMessageToUsRepo extends JpaRepository<SendMessageToUs, Long> {
    @Override
    Optional<SendMessageToUs> findById(Long aLong);
    List<SendMessageToUs> findAllByEmail(String email);
    List<SendMessageToUs> findAllByMessage(String message);
    List<SendMessageToUs> findAllByEmailAndMessage(String email, String message);


}
