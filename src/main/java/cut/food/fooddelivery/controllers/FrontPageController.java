package cut.food.fooddelivery.controllers;

import cut.food.fooddelivery.entities.Restaurant;
import cut.food.fooddelivery.entities.SendMessageToUs;
import cut.food.fooddelivery.services.*;
import cut.food.fooddelivery.utilities.UserRole;
import cut.food.fooddelivery.utilities.requests.SendMessageToUsRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
@RequestMapping(path = "/")
@AllArgsConstructor
public class FrontPageController {
    private final RestaurantService restaurantService;
    private final UserService userService;
    private final ImageService imageService;
    private final WorkingHoursService workingHoursService;
    private final SendMessageToUsService sendMessageToUsService;
    private final EmailService emailService;
    private static final String SEND_MESSAGE_TO_US_SUCCESS = "Uspješno ste nam poslali poruku. Odgovorit ćemo vam u što kraćem roku.";

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
        return "about-us";
    }

    @GetMapping(path = "/Kontaktirajte-nas")
    public String contactUs(Model model){
        model.addAttribute("banner", imageService.getImageById(6L).get().getName());
        model.addAttribute("sendMessageToUs", new SendMessageToUs());
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
        return "restaurant";
    }

    @GetMapping(path="/login")
    public String getLogin(){
        return "login";
    }

    @PostMapping(path = "/posaljite-nam-poruku")
    public String sendUsAMessage(@ModelAttribute SendMessageToUsRequest sendMessageToUsRequest, Model model) throws MessagingException, UnsupportedEncodingException {
        System.out.println(sendMessageToUsRequest.getMessage());
        //TODO kreirat objekt od send message to us, spremit ga u bazu i poslat na mail
        SendMessageToUs sendMessageToUs = new SendMessageToUs(sendMessageToUsRequest.getEmail(),sendMessageToUsRequest.getMessage());
        sendMessageToUsService.save(sendMessageToUs);
        emailService.sendMessageToUs(sendMessageToUs);
        model.addAttribute("message","Uspješno ste nam poslali poruku");
        return "success";
    }

}

