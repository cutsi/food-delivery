package cut.food.fooddelivery.Components;

import cut.food.fooddelivery.entities.User;
import cut.food.fooddelivery.services.UserService;
import cut.food.fooddelivery.utilities.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final UserService userService;
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {

        //CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User currentUser = userService.getCurrentUser().get();
        String redirectURL = request.getContextPath();

        if (currentUser.hasRole(UserRole.USER)) {
            redirectURL = "/";
        } else if (currentUser.hasRole(UserRole.RESTAURANT)) {
            redirectURL = "restaurant_orders";
        } else if (currentUser.hasRole(UserRole.ADMIN)) {
            redirectURL = "/";
        }

        response.sendRedirect(redirectURL);

    }
}
