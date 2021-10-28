package cut.food.fooddelivery.services;

import cut.food.fooddelivery.entities.Category;
import cut.food.fooddelivery.repos.CategoryRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepo categoryRepo;

    public Optional<Category> getCategoryByCategoryName(String categoryName){
        return categoryRepo.findByCategoryName(categoryName);
    }
    public Optional<Category> getCategoryById(Long id){
        return categoryRepo.findById(id);
    }
    public void save(Category category){
        categoryRepo.save(category);
    }

    public List<Category> getAll() {
        return categoryRepo.findAll();
    }
}
