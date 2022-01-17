package cut.food.fooddelivery.services;

import cut.food.fooddelivery.entities.User;
import cut.food.fooddelivery.repos.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(!userRepo.findByName(username).isPresent()){
            throw new IllegalStateException("Korisnik ne postoji");
        }
        return userRepo.findByName(username).get();
    }
    public void signUpUser(User user) {
        // check ih user exists
        boolean userExists = userRepo.findByEmail(user.getEmail()).isPresent();
        if (userExists) {
            // TODO check of attributes are the same and
            // TODO if email not confirmed send confirmation email.
            //if(!userRepo.findByEmail(user.getEmail()).get().getIsEnabled()){
            //}

            throw new IllegalStateException("Email je zauzet");
        }
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepo.save(user);
        String token = UUID.randomUUID().toString();
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
        Object principal = SecurityContextHolder.getContext().getAuthentication().getName();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else { username = principal.toString(); }
        Optional<User> user = userRepo.findByEmail(username);
        return user;
    }
    public void saveUser(User user){
        userRepo.save(user);
    }
}
