package cut.food.fooddelivery.repos;

import cut.food.fooddelivery.entities.FoodItem;
import cut.food.fooddelivery.entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@Transactional
public interface RestaurantRepo extends JpaRepository<Restaurant, Long> {

    //Query("SELECT a FROM Category a INNER JOIN a.users c WHERE c.email IN (?1)")
    //Set<Category> getCategoriesByUser(String email);

    @Transactional
    @Modifying
    @Query("SELECT a FROM FoodItem a INNER JOIN a.restaurants c WHERE c.id in (?1)")
    List<FoodItem> findFoodItemsByRestaurant(Long restaurantId);
    Optional<Restaurant> findRestaurantByRestaurantName(String name);

    List<Restaurant> findAllByOrderByIdAsc();
}
