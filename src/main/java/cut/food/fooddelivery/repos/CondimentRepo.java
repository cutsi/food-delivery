package cut.food.fooddelivery.repos;

import cut.food.fooddelivery.entities.Condiments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CondimentRepo extends JpaRepository<Condiments, Long> {

}
