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
    //TODO vidit kako menu item gumb prominit boju kad se klikne
    //TODO napravit portionsize food item table, malo razmislit kako to dvoje povezat
    //TODO unit sve podatke za svaki item, porciju i condiments
    //TODO prikazat ih sve pravilno na stranici

}
