package cut.food.fooddelivery.entities;


import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@EqualsAndHashCode
@AllArgsConstructor
public class Portion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String price;
    @ManyToOne(cascade = { CascadeType.REMOVE })
    @JoinColumn(name="food_item_id")
    private FoodItem foodItem;
}
