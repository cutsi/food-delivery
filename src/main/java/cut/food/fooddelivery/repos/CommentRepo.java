package cut.food.fooddelivery.repos;

import cut.food.fooddelivery.entities.Comment;
import cut.food.fooddelivery.entities.Condiments;
import cut.food.fooddelivery.entities.Restaurant;
import cut.food.fooddelivery.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface CommentRepo extends JpaRepository<Comment, Long> {
    List<Comment> findAllByUser(User user);
    List<Comment> findAllByCreatedAt(LocalDateTime createdAt);
    List<Comment> findAllByCreatedAtAfter(LocalDateTime createdAt);
    List<Comment> findAllByCreatedAtBefore(LocalDateTime createdAt);
    List<Comment> findAllByIsApprovedTrue();
    List<Comment> findAllByResponseId(Long responseId);
    List<Comment> findAllByRestaurant(Restaurant restaurant);

}
