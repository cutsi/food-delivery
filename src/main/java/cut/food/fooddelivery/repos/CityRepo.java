package cut.food.fooddelivery.repos;

import cut.food.fooddelivery.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepo extends JpaRepository<City, Long> {
    Optional<City> findByCityName(String cityName);
    Optional<City> findByZip(String zip);
}
