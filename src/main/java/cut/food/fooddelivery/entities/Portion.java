package cut.food.fooddelivery.entities;


import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@AllArgsConstructor
public class Portion implements Comparable<Portion>{
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
    @Column(nullable = true)
    private boolean checked;
    @ManyToOne(cascade = { CascadeType.REMOVE })
    @JoinColumn(name="food_item_id")
    private FoodItem food_item;

    @Override
    public int compareTo(Portion portion) {
        return (int) (this.id - portion.getId());
    }

}
