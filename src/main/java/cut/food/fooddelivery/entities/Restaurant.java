package cut.food.fooddelivery.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.jdbc.Work;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@AllArgsConstructor
public class Restaurant {

    @SequenceGenerator(
            name = "restaurant_sequence",
            sequenceName = "restaurant_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "restaurant_sequence"
    )
    private Long id;
    private String restaurantName;
    private String address;
    private String phone;
    private String image;
    private Double rating;
    private String banner;
    private String deliveryCost;
    @ManyToMany
    @JoinTable(
            name = "restaurant_menu",
            joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "foodItem_id"))
    private Set<FoodItem> menu = new HashSet<>();

    @OneToMany(
            mappedBy = "restaurant",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<WorkingHours> workingHours = new HashSet<>();
    @OneToMany(
            mappedBy = "restaurant",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<Rating> ratings = new HashSet<>();

    @OneToMany(mappedBy = "restaurant",
                cascade = CascadeType.ALL,
                orphanRemoval = true)
    private Set<User> restaurantOwner = new HashSet<>();

    public Restaurant(String restaurantName, String address, String phone, String image, String banner){
        this.restaurantName = restaurantName;
        this.address = address;
        this.phone = phone;
        this.image = image;
        this.banner = banner;
    }

}
