package cut.food.fooddelivery.repos;

import cut.food.fooddelivery.entities.Restaurant;
import cut.food.fooddelivery.entities.User;
import cut.food.fooddelivery.entities.WorkingHours;
import org.hibernate.jdbc.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkingHoursRepo extends JpaRepository<WorkingHours, Long> {
    Optional<WorkingHours> findByClosesAt(String closesAt);
    Optional<WorkingHours> findByDayOfWeek(String dayOfWeek);
    List<WorkingHours> findByRestaurantOrderById(Restaurant restaurant);
    Optional<WorkingHours> findById(Long id);
    Optional<WorkingHours> findByOpensAt(String opensAt);
    Optional<WorkingHours> findByDayOfWeekAndRestaurant(String dayOfWeek, Restaurant restaurant);
}
