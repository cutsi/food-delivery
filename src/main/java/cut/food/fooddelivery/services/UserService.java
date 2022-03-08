package cut.food.fooddelivery.services;

import cut.food.fooddelivery.entities.Token;
import cut.food.fooddelivery.entities.User;
import cut.food.fooddelivery.repos.UserRepo;
import lombok.AllArgsConstructor;

import net.bytebuddy.utility.RandomString;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final TokenService tokenService;
    private final EmailService emailService;
    private final UserRepo userRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private JavaMailSender mailSender;
    private static final String USER_NOT_FOUND =
            "Korisnik sa emailom: %s nije pronađen";
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<User> user = userRepo.findByEmail(email);
        if(!user.isPresent()) throw new UsernameNotFoundException(String.format(USER_NOT_FOUND, email));
        return user.get();
    }
    public void signUpUser(User user, String siteURL, String redirect) throws UnsupportedEncodingException, MessagingException{
        boolean userExists = userRepo.findByEmail(user.getEmail()).isPresent();
        //String verificationCode = RandomString.make(64);

        if (userExists) {
            // TODO check of attributes are the same and
            // TODO if email not confirmed send confirmation email.
            //user.setVerificationCode(verificationCode);
            if(!tokenService.checkForNonExpiredTokens(user))
                emailService.sendRegistrationConfirmEmail(user);
            throw new IllegalStateException("Email je zauzet");
        }
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepo.save(user);
        Token token = new Token(user);
        tokenService.saveToken(token);
        //Token verificationCode = new Token(userService.getUserByEmail(email).get());

        emailService.sendRegistrationConfirmEmail(user);
        // TODO: Send confirmation token later
    }

    public Optional<User> getUserByEmail(String email){
        return userRepo.findByEmail(email);
    }
    public Optional<User> getUserByName(String name){
        return userRepo.findByName(name);
    }
    public Optional<User> getUserById(Long id){
        return userRepo.findById(id);
    }
    public List<User> getAllUsers(){
        return userRepo.findAll();
    }
    public Optional<User> getUserByPhone(String phone){
        return userRepo.findByPhone(phone);
    }
    public Optional<User> getUserByAddress(String address){
        return userRepo.findByAddress(address);
    }
    public Optional<User> getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username;
        if(authentication.getPrincipal() instanceof UserDetails)
            username = ((UserDetails)authentication.getPrincipal()).getUsername();
        else
            username = authentication.getPrincipal().toString();
        Optional<User> user = userRepo.findByEmail(username);
        return user;
    }
    public void saveUser(User user){
        userRepo.save(user);
    }

    public User getUserByVerificationCode(String code){
        return userRepo.findByVerificationCode(code);
    }
}



