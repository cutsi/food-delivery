package cut.food.fooddelivery.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class WorkingHours {
    @SequenceGenerator(
            name = "wh_sequence",
            sequenceName = "wh_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "wh_sequence"
    )
    private Long id;
    private String opensAt;
    private String closesAt;
    private String dayOfWeek;

    @ManyToOne
    @JoinColumn(name="restaurant_id")
    private Restaurant restaurant;
}
