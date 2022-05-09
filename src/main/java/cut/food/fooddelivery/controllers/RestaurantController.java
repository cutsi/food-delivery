package cut.food.fooddelivery.controllers;

import cut.food.fooddelivery.entities.Restaurant;
import cut.food.fooddelivery.services.RestaurantService;
import cut.food.fooddelivery.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "")
@AllArgsConstructor
public class RestaurantController {
    private final RestaurantService restaurantService;
    private final UserService userService;
    @GetMapping(path = "")
    public String getRestaurant(){
        return "about-us";
    }

    @GetMapping(path = "narudzbe")
    public String getRestaurant1(Model model){
        model.addAttribute("item", restaurantService.getFoodItemsFromRestaurantById(1L).get(1));
        return "restaurant_orders";
    }
    @GetMapping(path = "menu")
    public String getMenu(Model model){
        Restaurant restaurant = restaurantService.getRestaurantByOwner(userService.getCurrentUser().get()).get();
        model.addAttribute("menu",restaurant.getMenu());
        model.addAttribute("categories", restaurantService.getCategoriesFromRestaurant(restaurant.getMenu()));
        return "menu";
    }
    @GetMapping(path = "statistika")
    public String getStats(){
        return "stats";
    }

    //TODO napravit service i repo za working hours, u kontroleru dobit od restorana sate, poslat ih na frontend
    //TODO i foreachat ih
}
