package cut.food.fooddelivery.utilities.requests;

import cut.food.fooddelivery.entities.Order;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CartRequest {


    private String id;
    private String image;
    private String name;
    private Order order;

}
