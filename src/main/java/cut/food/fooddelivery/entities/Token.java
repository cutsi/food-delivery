package cut.food.fooddelivery.entities;

import lombok.*;
import net.bytebuddy.utility.RandomString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
public class Token {
    @SequenceGenerator(
            name = "token_sequence",
            sequenceName = "token_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "token_sequence"
    )
    private Long id;
    private String token;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private boolean isExpired;
    private String info;
    @ManyToOne(cascade = { CascadeType.REMOVE }) //one user can have many tokens
    @JoinColumn(
            nullable = false,
            name = "user_id"
    )
    private User user;

    public Token(User user) {
        this.token = RandomString.make(32);
        this.createdAt = LocalDateTime.now(ZoneId.of("CET"));
        this.expiresAt = LocalDateTime.now(ZoneId.of("CET")).plusMinutes(15);
        this.user = user;
        this.isExpired = false;
    }

    public boolean isTokenExpired() {
        if(this.equals(null) || LocalDateTime.now(ZoneId.of("CET")).isAfter(expiresAt)){
            return false;
        }
        return true;
    }
}
