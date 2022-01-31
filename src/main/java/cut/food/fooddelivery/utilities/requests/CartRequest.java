package cut.food.fooddelivery.utilities.requests;

import cut.food.fooddelivery.entities.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartRequest {


    private String id;
    private String image;
    private String name;
    private Order order;

}
