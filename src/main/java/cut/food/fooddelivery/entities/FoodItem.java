package cut.food.fooddelivery.entities;


import lombok.*;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@EqualsAndHashCode
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
    private String info;

    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;
    @ManyToMany(mappedBy = "menu")
    private Set<Restaurant> restaurants = new HashSet<>();
    @ManyToMany(mappedBy = "foodItems")
    private Set<Condiments> condiments = new HashSet<>();
    @ManyToMany(mappedBy = "foodPortionItems")
    private Set<PortionSize> portionSizes = new HashSet<>();
    @OneToMany(
            mappedBy = "food_item",
            cascade = CascadeType.ALL,
            orphanRemoval = true) //ukoliko se ukloni user uklone se i ads koji su vezani za njega
    private Set<Portion> portions = new HashSet<>();

    public List<Portion> getPortionsOrderById(){
        List<Portion> portionList = new ArrayList<>(portions);
        Collections.sort(portionList);
        return portionList;
    }
}
