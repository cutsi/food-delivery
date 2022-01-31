package cut.food.fooddelivery.entities;

import cut.food.fooddelivery.entities.Condiments;
import cut.food.fooddelivery.entities.Portion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private String uniqueId;
    private Condiments[] condiments;
    private Portion portion;
    private String price;
    private Integer quantity;

}