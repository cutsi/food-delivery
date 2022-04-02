package cut.food.fooddelivery.controllers;

import cut.food.fooddelivery.services.CartRequestService;
import cut.food.fooddelivery.services.RestaurantService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "/narudzba")
@AllArgsConstructor
public class OrderController {
    private final CartRequestService cartRequestService;

    @PostMapping(path="", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String checkout(@RequestParam String[] foodItems, Model model) {
        model.addAttribute("foodItems", cartRequestService.getCartItems(foodItems));
        model.addAttribute("total", cartRequestService.getTotal(cartRequestService.getCartItems(foodItems)));
        return "checkout";
    }

}
