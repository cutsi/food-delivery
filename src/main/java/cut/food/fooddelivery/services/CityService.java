package cut.food.fooddelivery.services;

import cut.food.fooddelivery.entities.City;
import cut.food.fooddelivery.repos.CityRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CityService {
    private final CityRepo cityRepo;
    public Optional<City> getByCityName(String cityName){
        return cityRepo.findByCityName(cityName);
    }
    public Optional<City> getByZip(String zip){
        return cityRepo.findByZip(zip);
    }
    public Optional<City> getById(Long id){
        return cityRepo.findById(id);
    }
    public void save(City city){
        cityRepo.save(city);
    }
}
