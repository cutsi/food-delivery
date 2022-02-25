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
        System.out.println("USER CREDENTIALS: " + request.getEmail() + request.getPhone());
        request.setPassword(pass);
        registrationService.register(request, getSiteURL(siteURL));
        Optional<User> optionalUser = userService.getUserByEmail(request.getEmail());
        if (!userService.getUserByEmail(request.getEmail()).isPresent()){
            throw new IllegalStateException("Korisnik nije registriran");
        }
        User usr = (User) userService.loadUserByUsername(request.getEmail());

        model.addAttribute("restaurants", restaurantService.getAllRestaurants());
        return "/welcome";
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
