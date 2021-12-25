package cut.food.fooddelivery.services;

import cut.food.fooddelivery.entities.FoodItem;
import cut.food.fooddelivery.entities.Restaurant;
import cut.food.fooddelivery.repos.RestaurantRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RestaurantService {
    private final RestaurantRepo restaurantRepo;

    public List<Restaurant> getAllRestaurants(){
        return restaurantRepo.findAll();
    }
    public List<FoodItem> getFoodItemsFromRestaurantById(Long id){
        return restaurantRepo.findFoodItemsByRestaurant(id);
    }
    public Optional<Restaurant> getRestaurantById(Long id){
        return restaurantRepo.findById(id);
    }
}
