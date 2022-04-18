package cut.food.fooddelivery.services;

import cut.food.fooddelivery.entities.Comment;
import cut.food.fooddelivery.entities.Restaurant;
import cut.food.fooddelivery.entities.User;
import cut.food.fooddelivery.repos.CommentRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CommentService {
    private final CommentRepo commentRepo;

    public List<Comment> getAllByUser(User user){
        return commentRepo.findAllByUser(user);
    }
    public Optional<Comment> getById(Long id){
        return commentRepo.findById(id);
    }
    public List<Comment> getAllByCreatedAt(LocalDateTime createdAt){
        return commentRepo.findAllByCreatedAt(createdAt);
    }
    public List<Comment> getAllByCreatedAfter(LocalDateTime createdAt){
        return commentRepo.findAllByCreatedAtAfter(createdAt);
    }
    public List<Comment> getAllByCreatedBefore(LocalDateTime createdAt){
        return commentRepo.findAllByCreatedAtBefore(createdAt);
    }
    public List<Comment> getAllByIsApproved(){
        return commentRepo.findAllByIsApprovedTrue();
    }
    public List<Comment> getAllByResponseId(Long id){
        return commentRepo.findAllByResponseId(id);
    }
    public List<Comment> getAllByRestaurant(Restaurant restaurant){
        return commentRepo.findAllByRestaurant(restaurant);
    }
}
