package cut.food.fooddelivery.services;

import cut.food.fooddelivery.entities.FoodItem;
import cut.food.fooddelivery.repos.FoodItemRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class FoodItemService {
    private final FoodItemRepo foodItemRepo;

    public List<FoodItem> getAllFoodItems(){
        return foodItemRepo.findAll();
    }

}
