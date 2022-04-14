package cut.food.fooddelivery.utilities.requests;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class SendMessageToUsRequest {
    private final String email;
    private final String message;
}
