package cut.food.fooddelivery.controllers;

import cut.food.fooddelivery.entities.Token;
import cut.food.fooddelivery.entities.User;
import cut.food.fooddelivery.services.EmailService;
import cut.food.fooddelivery.services.TokenService;
import cut.food.fooddelivery.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

@Controller
@RequestMapping(path = "/zaboravljena-lozinka")
@AllArgsConstructor
public class ForgotPasswordController {
    private final EmailService emailService;
    private final TokenService tokenService;
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final String PASSWORD_SUCCESSFULLY_CHANGED = "Uspješno ste promijenili lozinku";
    private final String NO_USER_WITH_THIS_EMAIL = "Nema korisnika sa ovim email-om. Pokušajte ponovo ili se registrirajte";
    private final String CHECK_EMAIL_MESSAGE = "Pregledajte mail kako bi promijenili šifru.";
    private final String CHANGE_PASSWORD_EMAIL_ALREADY_SENT = "Već smo vam poslali poruku za promjenu lozinke. Provjerite svoj email.";
    private final String TOKEN_EXPIRED = "Ovaj kod je istekao. Pokušajte ponovo zatražiti promjenu lozinke.";
    private final String GENERIC_ERROR_MESSAGE = "Pokušajte ponovo ili nas kontaktirajte.";
    private final String PASSWORD_FORGOT_LINK = "/promijeni-lozinku";
    private final String VERIFY_FAIL_MESSAGE = "Nismo vam mogli verificirati račun. Vaš račun je već verificiran ili je verifikacijski kod netočan.";


    @GetMapping("")
    public String ChangePasswordGet(){
        return "change_password";
    }

    @PostMapping("")
    public String ChangePasswordPost(Model model, @RequestParam("email") String email)
            throws MessagingException, UnsupportedEncodingException {
        if(!userService.getUserByEmail(email).isPresent()){
            model.addAttribute("message", NO_USER_WITH_THIS_EMAIL);
            return "fail";
        }
        try {
            tokenService.deleteAllExpiredTokensFromUser(userService.getUserByEmail(email).get());
        }catch (Exception e){}

        if(tokenService.getTokenByUser(userService.getUserByEmail(email).get()).isPresent()){
            model.addAttribute("message", CHANGE_PASSWORD_EMAIL_ALREADY_SENT);
            return "fail";
        }
        Token verificationCode = new Token(userService.getUserByEmail(email).get());
        tokenService.saveToken(verificationCode);
        emailService.sendPasswordChangeEmail(userService.getUserByEmail(email).get());

        model.addAttribute("message", CHECK_EMAIL_MESSAGE);
        return "success";
    }

    @GetMapping("/promijeni-lozinku")
    public String changePassword(Model model, @Param("code") String code){
        Optional<Token> tokenOptional = tokenService.getTokenByCode(code);
        if(tokenService.isTokenExpired(tokenOptional)){
            model.addAttribute("message", TOKEN_EXPIRED);
            return "fail";
        }
        Token token = tokenOptional.get();
        User user = userService.getUserById(token.getUser().getId()).get();
        model.addAttribute("user", user);
        return "change_password_form";
    }
    @PostMapping("/promijeni-lozinku")
    public String changePasswordPost(Model model, @RequestParam("userId") String userId,
                                     @RequestParam("password1") String password){
        if(!userService.getUserById(Long.valueOf(userId)).isPresent()){
            model.addAttribute("message", GENERIC_ERROR_MESSAGE);
            return "fail";
        }
        User user = userService.getUserById(Long.valueOf(userId)).get();
        String encodedPassword = bCryptPasswordEncoder.encode(password);
        user.setPassword(encodedPassword);
        tokenService.deleteToken(tokenService.getTokenByUser(user).get());
        userService.saveUser(user);
        model.addAttribute("message", PASSWORD_SUCCESSFULLY_CHANGED);
        return "success";
    }
}
