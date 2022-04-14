package cut.food.fooddelivery.services;

import cut.food.fooddelivery.entities.SendMessageToUs;
import cut.food.fooddelivery.repos.SendMessageToUsRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SendMessageToUsService {
    private final SendMessageToUsRepo sendMessageToUsRepo;

    public Optional<SendMessageToUs> getById(Long id){
        return sendMessageToUsRepo.findById(id);
    }
    public List<SendMessageToUs> getAllByEmail(String email){
        return sendMessageToUsRepo.findAllByEmail(email);
    }
    public List<SendMessageToUs> getAllByMessage(String message){
        return sendMessageToUsRepo.findAllByMessage(message);
    }
    public List<SendMessageToUs> getAllByEmailAndMessage(String email, String message){
        return sendMessageToUsRepo.findAllByEmailAndMessage(email, message);
    }

    public void save(SendMessageToUs sendMessageToUs) {
        sendMessageToUsRepo.save(sendMessageToUs);
    }
}
