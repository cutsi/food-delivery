package cut.food.fooddelivery.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
@Setter
@NoArgsConstructor
@Entity
@AllArgsConstructor
public class Comment {
    @SequenceGenerator(
            name = "comment_sequence",
            sequenceName = "comment_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "comment_sequence"
    )
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private Boolean isApproved;
    private Long responseId;

    @ManyToOne(cascade = { CascadeType.REMOVE }) //one user can have many comments
    @JoinColumn(
            nullable = false,
            name = "user_id"
    )
    private User user;
    @ManyToOne(cascade = { CascadeType.REMOVE }) //one user can have many comments
    @JoinColumn(
            nullable = false,
            name = "restaurant_id"
    )
    private Restaurant restaurant;

    public Comment(String content){
        this.content = content;
        this.createdAt = LocalDateTime.now(ZoneId.of("CET"));
        this.isApproved = false;
    }
    public Comment(String content, Restaurant restaurant){
        this.content = content;
        this.createdAt = LocalDateTime.now(ZoneId.of("CET"));
        this.isApproved = false;
        this.restaurant = restaurant;
    }
}
