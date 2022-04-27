package cut.food.fooddelivery.controllers;

import cut.food.fooddelivery.services.RestaurantService;
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
    @GetMapping(path = "")
    public String getRestaurant(){
        return "about-us";
    }

    @GetMapping(path = "restaurant_orders")
    public String getRestaurant1(Model model){
        model.addAttribute("item", restaurantService.getFoodItemsFromRestaurantById(1L).get(1));
        return "restaurant_orders";
    }

    //TODO napravit service i repo za working hours, u kontroleru dobit od restorana sate, poslat ih na frontend
    //TODO i foreachat ih
}
