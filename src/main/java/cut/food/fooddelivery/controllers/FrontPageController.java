package cut.food.fooddelivery.controllers;

import cut.food.fooddelivery.entities.Category;
import cut.food.fooddelivery.entities.FoodItem;
import cut.food.fooddelivery.entities.Restaurant;
import cut.food.fooddelivery.services.CategoryService;
import cut.food.fooddelivery.services.FoodItemService;
import cut.food.fooddelivery.services.RestaurantService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "/")
@AllArgsConstructor
public class FrontPageController {
    private final CategoryService categoryService;
    private final FoodItemService foodItemService;
    private final RestaurantService restaurantService;

    @GetMapping(path = "/index")
    public String index(){
        return "index";
    }

    @GetMapping(path = "/")
    public String welcome(Model model){
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        model.addAttribute("restaurants",restaurants);
        return "welcome";
    }
    @GetMapping(path = "/expo")
    public String experiment(Model model){
        model.addAttribute("foodItems", foodItemService.getAllFoodItems());
        //restaurantService.getFoodItemsFromRestaurantById();
        //TODO pogledat kako ovde dobavit Id of restorana, vidit kako for eachat sve iteme
        //TODO zajedno sa njihovim kategorijama
        return "expriment";
    }

    @GetMapping("/sign-up")
    public String register(){
        return "signup";
    }
    @GetMapping(path="/login")
    public String login(){
        return "login";
    }

    @GetMapping(path="/restaurant")
    public String restoran(Model model, @RequestParam("id") String restaurantId){
        Optional<Restaurant> restaurantOptional = restaurantService.getRestaurantById(Long.valueOf(restaurantId));
        if(!restaurantOptional.isPresent()){
            return "error";
        }
        Restaurant restaurant = restaurantOptional.get();
        List<FoodItem> foodItemList = restaurantService.getFoodItemsFromRestaurantById(restaurant.getId());
        for (FoodItem f:foodItemList
             ) {
            System.out.println(f.getName());
        }
        model.addAttribute("restaurant", restaurant);
        return "restaurant";
    }
    @GetMapping(path="/test")
    public String test(){
        return "test";
    }
}
