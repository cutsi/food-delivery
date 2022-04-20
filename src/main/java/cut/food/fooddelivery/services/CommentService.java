package cut.food.fooddelivery.services;

import cut.food.fooddelivery.entities.Comment;
import cut.food.fooddelivery.entities.Restaurant;
import cut.food.fooddelivery.entities.User;
import cut.food.fooddelivery.repos.CommentRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CommentService {
    private final CommentRepo commentRepo;
    private static final DecimalFormat df = new DecimalFormat("0.00");

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
    public String getAverageRating(Restaurant restaurant){
        List<Comment> comments = commentRepo.findAllByRestaurant(restaurant);
        Double avgRating = 0.00;
        for (Comment comment:comments) {
            avgRating = avgRating + Double.valueOf(comment.getRating());
            System.out.println("RATING: " + comment.getRating());
        }
        System.out.println(avgRating + "/" + Double.valueOf(comments.size()));
        return df.format(avgRating/Double.valueOf(comments.size()));
        //return avgRating/Double.valueOf(comments.size());
    }
    public void save(Comment comment){
        commentRepo.save(comment);
    }
}
