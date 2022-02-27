package cut.food.fooddelivery.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cut.food.fooddelivery.entities.Category;
import cut.food.fooddelivery.entities.FoodItem;
import cut.food.fooddelivery.entities.Restaurant;
import cut.food.fooddelivery.entities.User;
import cut.food.fooddelivery.security.CartRequestService;
import cut.food.fooddelivery.services.*;
import cut.food.fooddelivery.utilities.requests.CartRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.xml.xpath.XPath;

import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Controller
@RequestMapping(path = "/")
@AllArgsConstructor
public class FrontPageController {

    private final CategoryService categoryService;
    private final FoodItemService foodItemService;
    private final RestaurantService restaurantService;
    private final UserService userService;
    private final ImageService imageService;
    private final CartRequestService cartRequestService;

    @GetMapping(path = "/")
    public String welcome(Model model){
        System.out.println("/ WELCOME");

        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        model.addAttribute("restaurants",restaurants);
        if(userService.getCurrentUser().isPresent())
            System.out.println(userService.getCurrentUser().get().getEmail());
        return "welcome";
    }


    @GetMapping(path="/login")
    public String login(){
        System.out.println("LOGIN GET");

        return "login";
    }

    @GetMapping(path = "/welcome")
    public String welcome(){
        System.out.println("GET WELCOME");
        //System.out.println(userService.getCurrentUser().get().getEmail());

        return "welcome";
    }
    @PostMapping(path = "/welcome")
    public String loginPost(){
        System.out.println("LOGIN POST");
        //System.out.println(userService.getCurrentUser().get().getEmail());

        return "welcome";
    }

    //----------------------------------------------------------------------
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


    @PostMapping(path = "/custom-logout")
    public String customLogout(){
        return "custom-logout";
    }
    @PostMapping(path = "/logout-success")
    public String LogoutSuccess(){
        return "logout-success";
    }
    @GetMapping(path = "my-profile")
    public String myProfile(Model model){
        model.addAttribute("user", userService.getCurrentUser().get());
        return "my-profile";
    }
    @GetMapping(path = "/Kako-naručiti")
    public String kako_naručiti(Model model){

        model.addAttribute("banner", imageService.getImageById(8L).get().getName());
        return "kako-naručiti";
    }
    @GetMapping(path = "/O-nama")
    public String o_nama(Model model){
        model.addAttribute("banner", imageService.getImageById(7L).get().getName());
        model.addAttribute("beef", imageService.getImageById(3L).get().getName());
        model.addAttribute("dough", imageService.getImageById(5L).get().getName());

        return "o-nama";
    }
    @GetMapping(path = "/Kontaktirajte-nas")
    public String contact_us(Model model){
        model.addAttribute("banner", imageService.getImageById(6L).get().getName());
        return "kontaktirajte-nas";
    }

    @PostMapping(path="/checkout", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String checkout(@RequestParam String[] foodItems, Model model) {
        List<CartRequest> cartItems = cartRequestService.getCartItems(foodItems);
        model.addAttribute("foodItems", cartItems);
        return "checkout";
    }

    @GetMapping("/success")
    public String success(){
        return "success";
    }

}

