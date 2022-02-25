package cut.food.fooddelivery.controllers;

import cut.food.fooddelivery.entities.User;
import cut.food.fooddelivery.services.RegistrationService;
import cut.food.fooddelivery.services.RestaurantService;
import cut.food.fooddelivery.services.UserService;
import cut.food.fooddelivery.utilities.EmailValidator;
import cut.food.fooddelivery.utilities.requests.RegistrationRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

@Controller
@RequestMapping(path = "/")
@AllArgsConstructor
public class RegistrationController {
    private final UserService userService;
    private final RegistrationService registrationService;
    private final RestaurantService restaurantService;

    @GetMapping(path = "/sign-up")
    public String signup(@ModelAttribute RegistrationRequest registrationRequest,
                         Model model){
        model.addAttribute("user", new User());
        return "signup";
    }
    @PostMapping(path = "/sign-up")
    public String register(@ModelAttribute RegistrationRequest request, Model model, HttpServletRequest siteURL,
                           @RequestParam("password1") String pass) throws MessagingException, UnsupportedEncodingException {
        System.out.println("                111111111111111111111                        ");
        request.setPassword(pass);
        System.out.println("                2222222222222222222222                        ");
        registrationService.register(request, getSiteURL(siteURL));
        System.out.println("                3333333333333333333333                        ");
        if (!userService.getUserByEmail(request.getEmail()).isPresent()){
            System.out.println("             4444444444444444444444                        ");
            throw new IllegalStateException("Korisnik nije registriran");
        }
        System.out.println("                55555555555555555555                        ");
        model.addAttribute("restaurants", restaurantService.getAllRestaurants());
        if(true)
            return "/welcome";
        return "/verify_success";
    }
    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }
    @GetMapping("/verify")
    public String verifyUser(@Param("code") String code) {
        if (userService.verify(code)) {
            return "verify_success";
        } else {
            return "verify_fail";
        }
    }


}
