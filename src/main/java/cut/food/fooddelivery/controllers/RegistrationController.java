package cut.food.fooddelivery.controllers;

import cut.food.fooddelivery.entities.User;
import cut.food.fooddelivery.services.RegistrationService;
import cut.food.fooddelivery.services.UserService;
import cut.food.fooddelivery.utilities.EmailValidator;
import cut.food.fooddelivery.utilities.requests.RegistrationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(path = "/")
@AllArgsConstructor
public class RegistrationController {
    private final UserService userService;
    private final RegistrationService registrationService;


    @GetMapping("/sign-up")
    public String register(){
        return "signup";
    }

    @PostMapping("/sign-up")
    public String register(@ModelAttribute RegistrationRequest request, Model model,
                           @RequestParam("password1") String pass) {
        System.out.println("USER CREDENTIALS: " + request.getEmail() + request.getPhone());
        request.setPassword(pass);
        registrationService.register(request);
        Optional<User> optionalUser = userService.getUserByEmail(request.getEmail());
        if (!userService.getUserByEmail(request.getEmail()).isPresent()){
            throw new IllegalStateException("Korisnik nije registriran");
        }

        model.addAttribute("user", userService.getUserByEmail(request.getEmail()).get());
        return "/";
    }


}
