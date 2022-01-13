package cut.food.fooddelivery.entities;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@AllArgsConstructor
public class PortionSize {
    @SequenceGenerator(
            name = "portionSize_sequence",
            sequenceName = "portionSize_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "portionSize_sequence"
    )
    private Long id;
    private String size;
    private Boolean isChecked;

    @ManyToMany
    @JoinTable(
            name = "portionSize_foodItem",
            joinColumns = @JoinColumn(name = "portionSize_id"),
            inverseJoinColumns = @JoinColumn(name = "foodItem_id"))
    private Set<FoodItem> foodPortionItems = new HashSet<>();



    //TODO vidit kako menu item gumb prominit boju kad se klikne
    //TODO napravit portionsize food item table, malo razmislit kako to dvoje povezat
    //TODO unit sve podatke za svaki item, porciju i condiments
    //TODO prikazat ih sve pravilno na stranici


    //STAVIT U FOODITEM


}
