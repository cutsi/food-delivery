package cut.food.fooddelivery.services;

import cut.food.fooddelivery.entities.Condiments;
import cut.food.fooddelivery.repos.CondimentRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CondimentService {
    private final CondimentRepo condimentRepo;

    public List<Condiments> getAllCondiments() {
        return condimentRepo.findAll();
    }
}