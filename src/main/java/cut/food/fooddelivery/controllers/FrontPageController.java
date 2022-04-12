package cut.food.fooddelivery.controllers;

import cut.food.fooddelivery.entities.Restaurant;
import cut.food.fooddelivery.entities.WorkingHours;
import cut.food.fooddelivery.services.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping(path = "/")
@AllArgsConstructor
public class FrontPageController {
    private final RestaurantService restaurantService;
    private final UserService userService;
    private final ImageService imageService;
    private final WorkingHoursService workingHoursService;

    @GetMapping(path = {"/", "/welcome"})
    public String welcome(Model model){
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        model.addAttribute("restaurants",restaurants);
        return "welcome";
    }

    @GetMapping(path = "my-profile")
    public String myProfile(Model model){
        model.addAttribute("user", userService.getCurrentUser().get());
        return "my-profile";
    }

    @GetMapping(path = "/Kako-naručiti")
    public String howToOrder(Model model){
        model.addAttribute("banner", imageService.getImageById(8L).get().getName());
        return "kako-naručiti";
    }

    @GetMapping(path = "/O-nama")
    public String aboutUs(Model model){
        model.addAttribute("banner", imageService.getImageById(7L).get().getName());
        model.addAttribute("beef", imageService.getImageById(3L).get().getName());
        model.addAttribute("dough", imageService.getImageById(5L).get().getName());
        return "o-nama";
    }

    @GetMapping(path = "/Kontaktirajte-nas")
    public String contactUs(Model model){
        model.addAttribute("banner", imageService.getImageById(6L).get().getName());
        return "kontaktirajte-nas";
    }
    //TODO onemogucit narucivanje kad je restoran zatvoren
    @GetMapping(path="/restaurant")
    public String restaurant(Model model, @RequestParam("ime") String restaurantId) throws Exception {
        Restaurant restaurant = restaurantService.getRestaurantByName(restaurantId).get();
        model.addAttribute("categories", restaurantService.getCategoriesFromRestaurant(restaurant.getMenu()));
        model.addAttribute("menu",restaurant.getMenu());
        model.addAttribute("restaurant", restaurant);
        //System.out.println(workingHoursService.isRestaurantClosed(workingHoursService.getRestaurantWorkingHoursToday(restaurant)));
        //System.out.println(workingHoursService.restaurantClosed(workingHoursService.getRestaurantWorkingHoursToday(restaurant), restaurant));
        model.addAttribute("isClosed", workingHoursService.restaurantClosed(workingHoursService.getRestaurantWorkingHoursToday(restaurant), restaurant));
        model.addAttribute("workingHoursToday", workingHoursService.getRestaurantWorkingHoursToday(restaurant));
        model.addAttribute("workingHours", workingHoursService.getByRestaurant(restaurant));
        System.out.println("TRENUTNO VRIJEME KAD JE DEPLOYANO: " + LocalDateTime.now());
        return "restaurant";
    }

    @GetMapping(path="/login")
    public String getLogin(){
        return "login";
    }

}

