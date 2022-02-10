package cut.food.fooddelivery.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cut.food.fooddelivery.entities.FoodItem;
import cut.food.fooddelivery.utilities.requests.CartRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class CartRequestService {

    public List<CartRequest> getCartItems(String[] foodItems){
        List<CartRequest> cartItems = new ArrayList<CartRequest>();

        ObjectMapper mapper = new ObjectMapper();
        Stream.of(foodItems).forEach(f->{
            try {

                if(!"null".equals(f)) {
                    cartItems.add(mapper.readValue(f, CartRequest.class));
                }

            } catch (JsonProcessingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        });
        return cartItems;
    }
}
