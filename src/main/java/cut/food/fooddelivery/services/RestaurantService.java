package cut.food.fooddelivery.services;

import cut.food.fooddelivery.entities.Category;
import cut.food.fooddelivery.entities.FoodItem;
import cut.food.fooddelivery.entities.Restaurant;
import cut.food.fooddelivery.repos.RestaurantRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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
    public Set<Category> getCategoriesFromRestaurant(Set<FoodItem> menu){
        Set<Category> categories = null;
        for (FoodItem foodItem: menu) {
            if(!categories.contains(foodItem.getCategory())){
                categories.add(foodItem.getCategory());
            }
        }
        return categories;
    }
}
