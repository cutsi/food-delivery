package cut.food.fooddelivery.controllers;

import cut.food.fooddelivery.entities.Category;
import cut.food.fooddelivery.entities.FoodItem;
import cut.food.fooddelivery.entities.Restaurant;
import cut.food.fooddelivery.services.CategoryService;
import cut.food.fooddelivery.services.FoodItemService;
import cut.food.fooddelivery.services.RestaurantService;
import cut.food.fooddelivery.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.xml.xpath.XPath;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "/")
@AllArgsConstructor
public class FrontPageController {
    private final CategoryService categoryService;
    private final FoodItemService foodItemService;
    private final RestaurantService restaurantService;
    private final UserService userService;

    @GetMapping(path = "/")
    public String welcome(Model model){
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        model.addAttribute("restaurants",restaurants);
        System.out.println(userService.getCurrentUser().get());
        return "welcome";
    }


    @GetMapping(path="/login")
    public String login(){
        return "login";
    }

    @PostMapping(path = "/welcome")
    public String loginPost(){
        System.out.println(userService.getCurrentUser().get().getEmail());

        return "welcome";
    }
    @GetMapping(path="/restaurant")
    public String restoran(Model model, @RequestParam("id") String restaurantId){
        Optional<Restaurant> restaurantOptional = restaurantService.getRestaurantById(Long.valueOf(restaurantId));
        if(!restaurantOptional.isPresent()){
            return "error";
        }
        Restaurant restaurant = restaurantOptional.get();
        model.addAttribute("categories", restaurantService.getCategoriesFromRestaurant(restaurant.getMenu()));
        model.addAttribute("menu",restaurant.getMenu());
        model.addAttribute("restaurant", restaurant);
        return "restaurant";
    }
    @GetMapping(path="/test")
    public String test(){
        return "test";
    }

    @PostMapping(path="/checkout")
    public String checkout(@RequestParam Long[] products,Model model) {
        List<FoodItem> foodItemList = new ArrayList<>();
        for (Long id:products) {
            if(foodItemService.getById(id).isPresent())
                foodItemList.add(foodItemService.getById(id).get());
        }
        model.addAttribute("products", foodItemList);
        return "checkout";
    }
    @GetMapping(path = "checkout")
    public String checkoutGet(){
        return "checkout";
    }
}
