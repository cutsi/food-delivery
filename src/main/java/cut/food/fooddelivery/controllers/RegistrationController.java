package cut.food.fooddelivery.controllers;

import antlr.BaseAST;
import com.sun.xml.bind.v2.TODO;
import cut.food.fooddelivery.entities.User;
import cut.food.fooddelivery.services.EmailService;
import cut.food.fooddelivery.services.RegistrationService;
import cut.food.fooddelivery.services.RestaurantService;
import cut.food.fooddelivery.services.UserService;
import cut.food.fooddelivery.utilities.EmailValidator;
import cut.food.fooddelivery.utilities.requests.RegistrationRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

@Controller
@RequestMapping(path = "prijava")
@AllArgsConstructor
public class RegistrationController {
    private final EmailService emailService;
    private final UserService userService;
    private final String VERIFY_LINK = "/verify";
    private final String REGISTRATION_FAIL_MESSAGE = "Vaš račun je već registriran";
    private final String REGISTRATION_SUCCESS_MESSAGE = "Vaš račun je uspješno kreiran. Pregledajte svoj mail i verificirajte račun. Nakon toga se možete ulogirati.";
    private final String VERIFY_SUCCESS_MESSAGE = "Uspješno ste se verificirali. Vaš račun je sada osposobljen za naručivanje. Dobar tek!";
    private final String VERIFY_FAIL_MESSAGE = "Nismo vam mogli verificirati račun. Vaš račun je već verificiran ili je verifikacijski kod netočan.";

    @GetMapping(path = "")
    public String getSignup(@ModelAttribute RegistrationRequest registrationRequest, Model model){
        model.addAttribute("user", new User());
        return "signup";
    }
    @PostMapping(path = "")
    public String postSignUp(@ModelAttribute RegistrationRequest request, Model model, HttpServletRequest siteURL,
                             @RequestParam("password1") String pass) throws MessagingException, UnsupportedEncodingException {
        request.setPassword(pass);
        if (userService.getUserByEmail(request.getEmail()).isPresent()){
            model.addAttribute("message", REGISTRATION_FAIL_MESSAGE);
            return "fail";
        }
        userService.signUpUser(new User
                (request.getEmail(), request.getPhone(), request.getPassword(),
                        request.getName()));
        model.addAttribute("message", REGISTRATION_SUCCESS_MESSAGE);
        return "success";
    }
    @GetMapping("verifikacija")
    public String verifyUser(@Param("code") String code, Model model) {
        if (emailService.verify(code)){
            model.addAttribute("message", VERIFY_SUCCESS_MESSAGE);
            return "success";
        }
        model.addAttribute("message", VERIFY_FAIL_MESSAGE);
        return "fail";
    }
}
