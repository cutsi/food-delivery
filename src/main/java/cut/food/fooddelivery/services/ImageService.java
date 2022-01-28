package cut.food.fooddelivery.services;

import cut.food.fooddelivery.entities.Image;
import cut.food.fooddelivery.repos.ImageRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ImageService {
    private ImageRepo imageRepo;
    public List<Image> getAllImages(){
        return imageRepo.findAll();
    }
    public Optional<Image> getImageById(Long id){
        return imageRepo.findById(id);
    }
}
