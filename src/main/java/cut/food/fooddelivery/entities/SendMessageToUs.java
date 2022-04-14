package cut.food.fooddelivery.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@AllArgsConstructor
@Table(name="messages_sent_to_us")
public class SendMessageToUs {
    @SequenceGenerator(
            name = "messages_sent_to_us_sequence",
            sequenceName = "messages_sent_to_us_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "messages_sent_to_us_sequence"
    )
    private Long id;
    private String email;
    private String message;
    private LocalDateTime timeSent;

    public SendMessageToUs(String email, String message){
        this.email = email;
        this.message = message;
        this.timeSent = LocalDateTime.now(ZoneId.of("CET"));
    }
}
