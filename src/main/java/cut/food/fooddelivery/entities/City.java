package cut.food.fooddelivery.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@AllArgsConstructor
public class City {
    @SequenceGenerator(
            name = "city_sequence",
            sequenceName = "city_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "city_sequence"
    )
    private Long id;
    private String cityName;
    private String zip;
    @ManyToMany(mappedBy = "cities")
    private Set<User> users = new HashSet<>();

    @ManyToMany(mappedBy = "cities")
    private Set<Restaurant> restaurants = new HashSet<>();
}
