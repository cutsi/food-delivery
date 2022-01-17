package cut.food.fooddelivery.services;

import cut.food.fooddelivery.entities.User;
import cut.food.fooddelivery.utilities.EmailValidator;
import cut.food.fooddelivery.utilities.UserRole;
import cut.food.fooddelivery.utilities.requests.RegistrationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {
    private final UserService userService;
    private final EmailValidator emailValidator;

    public void register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator
                .test(request.getEmail());
        if(!isValidEmail){
            throw new IllegalStateException("Email not valid");
        }
        userService.signUpUser(
                new User(request.getEmail(),
                        request.getPhone(),
                        request.getPassword()));
    }

}
