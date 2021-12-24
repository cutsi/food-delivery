package cut.food.fooddelivery.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@AllArgsConstructor
public class FoodItem {
    @SequenceGenerator(
            name = "item_sequence",
            sequenceName = "item_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "item_sequence"
    )
    private Long id;
    private String name;
    private String price;
    private String image;

    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;
    @ManyToMany(mappedBy = "menu")
    private Set<Restaurant> restaurants = new HashSet<>();
    @ManyToMany(mappedBy = "foodItems")
    private Set<Condiments> condiments = new HashSet<>();
    public int getPriceInt(){
        return Integer.valueOf(price);
    }
}
