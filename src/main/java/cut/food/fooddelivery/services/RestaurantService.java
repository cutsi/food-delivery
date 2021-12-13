package cut.food.fooddelivery.services;

import cut.food.fooddelivery.entities.Restaurant;
import cut.food.fooddelivery.repos.RestaurantRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RestaurantService {
    private final RestaurantRepo restaurantRepo;

    public List<Restaurant> getAllRestaurants(){
        return restaurantRepo.findAll();
    }
}
