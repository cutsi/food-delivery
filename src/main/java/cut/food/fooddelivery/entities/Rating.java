package cut.food.fooddelivery.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@AllArgsConstructor
public class Rating {
    @SequenceGenerator(
            name = "rating_sequence",
            sequenceName = "rating_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "rating_sequence"
    )
    private Long id;
    private Double rating;
    private int numberOfRatings;

    @ManyToOne(cascade = { CascadeType.REMOVE }) //one user can have many comments
    @JoinColumn(
            nullable = false,
            name = "restaurant_id"
    )
    private Restaurant restaurant;
    @ManyToOne(cascade = { CascadeType.REMOVE }) //one user can have many comments
    @JoinColumn(
            nullable = false,
            name = "user_id"
    )
    private User user;
}
