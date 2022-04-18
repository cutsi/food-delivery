package cut.food.fooddelivery.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/restaurantUser")
@AllArgsConstructor
public class RestaurantController {

    @GetMapping(path = "")
    public String getRestaurant(){
        return "about-us";
    }

    @GetMapping(path = "kontaktirajte-nas")
    public String getRestaurant1(){
        return "kontaktirajte-nas";
    }

    //TODO napravit service i repo za working hours, u kontroleru dobit od restorana sate, poslat ih na frontend
    //TODO i foreachat ih
}
