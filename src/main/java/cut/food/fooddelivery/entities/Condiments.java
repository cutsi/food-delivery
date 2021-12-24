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
public class Condiments {
    @SequenceGenerator(
            name = "condiment_sequence",
            sequenceName = "condiment_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "condiment_sequence"
    )
    private Long id;
    private String name;
    private String price;

    public Condiments(String name){
        this.name = name;
    }
    @ManyToMany
    @JoinTable(
            name = "Condiments_foodItem",
            joinColumns = @JoinColumn(name = "condiments_id"),
            inverseJoinColumns = @JoinColumn(name = "foodItem_id"))
    private Set<FoodItem> foodItems = new HashSet<>();
}
