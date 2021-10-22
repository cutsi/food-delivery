package cut.food.fooddelivery.utilities.requests;

import cut.food.fooddelivery.utilities.UserRole;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegReq {
    private final Long id;
    private final String name;
    private final String phone;
    private final String email;
    private final String password;
    private final String address;
    private final UserRole UserRole;

}
