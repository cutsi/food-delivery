package cut.food.fooddelivery.entities;


import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@AllArgsConstructor
public class Portion {
    @SequenceGenerator(
            name = "portion_sequence",
            sequenceName = "portion_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "portion_sequence"
    )
    private Long id;
    private String name;
    private String price;
    @ManyToOne(cascade = { CascadeType.REMOVE })
    @JoinColumn(name="food_item_id")
    private FoodItem foodItem;
}
