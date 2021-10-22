package cut.food.fooddelivery.services;

import cut.food.fooddelivery.entities.User;
import cut.food.fooddelivery.utilities.UserRole;
import cut.food.fooddelivery.utilities.requests.RegReq;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {
    private final UserService userService;

    public void register(RegReq request) {

        userService.signUpUser(
                new User(
                        request.getName(),
                        request.getPhone(),
                        request.getEmail(),
                        request.getPassword(),
                        request.getAddress()
                )
        );
    }
}
