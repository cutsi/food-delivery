package cut.food.fooddelivery.controllers;

import cut.food.fooddelivery.entities.Restaurant;
import cut.food.fooddelivery.services.CartRequestService;
import cut.food.fooddelivery.services.RestaurantService;
import cut.food.fooddelivery.utilities.requests.CartRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
@RequestMapping(path = "/")
@AllArgsConstructor
public class RestaurantController {
    private final RestaurantService restaurantService;
    private final CartRequestService cartRequestService;

    @GetMapping(path="/restaurant")
    public String restaurant(Model model, @RequestParam("ime") String restaurantId){
        Restaurant restaurant = restaurantService.getRestaurantByName(restaurantId).get();
        model.addAttribute("categories", restaurantService.getCategoriesFromRestaurant(restaurant.getMenu()));
        model.addAttribute("menu",restaurant.getMenu());
        model.addAttribute("restaurant", restaurant);
        return "restaurant";
    }

    @PostMapping(path="/checkout", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String checkout(@RequestParam String[] foodItems, Model model) {
        List<CartRequest> cartItems = cartRequestService.getCartItems(foodItems);
        model.addAttribute("foodItems", cartItems);
        model.addAttribute("total", cartRequestService.getTotal(cartItems));
        return "checkout";
    }
}
