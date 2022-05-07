package cut.food.fooddelivery.entities;

import cut.food.fooddelivery.entities.Condiments;
import cut.food.fooddelivery.entities.Portion;
import lombok.*;
import java.text.DecimalFormat;
import org.hibernate.annotations.GeneratorType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Order {
    private static final DecimalFormat df = new DecimalFormat("0.00");

    private String uniqueId;
    private Condiments[] condiments;
    private Portion portion;
    private String price;
    private Integer quantity;

    public String getPrice() {
        return df.format(Double.valueOf(price));
    }
}