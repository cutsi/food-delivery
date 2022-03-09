package cut.food.fooddelivery.entities;

import cut.food.fooddelivery.entities.Condiments;
import cut.food.fooddelivery.entities.Portion;
import lombok.*;
import org.hibernate.annotations.GeneratorType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Order {

    private String uniqueId;
    private Condiments[] condiments;
    private Portion portion;
    private String price;
    private Integer quantity;
}