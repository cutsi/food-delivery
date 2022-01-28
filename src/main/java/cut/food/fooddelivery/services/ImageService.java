package cut.food.fooddelivery.services;

import cut.food.fooddelivery.entities.Image;
import cut.food.fooddelivery.repos.ImageRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ImageService {
    private ImageRepo imageRepo;
    public List<Image> getAllImages(){
        return imageRepo.findAll();
    }
    public Image getImageById(Long id){
        if(imageRepo.findById(id).isPresent())
            return imageRepo.findById(id).get();
        return null;
    }
}
