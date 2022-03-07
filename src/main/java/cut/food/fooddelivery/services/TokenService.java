package cut.food.fooddelivery.services;

import cut.food.fooddelivery.entities.Token;
import cut.food.fooddelivery.entities.User;
import cut.food.fooddelivery.repos.TokenRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TokenService {
    private final TokenRepo tokenRepo;

    public Optional<Token> getTokenByUser(User user){
        return tokenRepo.findTokenByUser(user);
    }
    public Optional<Token> getTokenByCode(String code){
        return tokenRepo.findTokenByToken(code);
    }
    public void deleteToken(Token token){
        tokenRepo.delete(token);
    }
    public void saveToken(Token token){
        tokenRepo.save(token);
    }
    public boolean isTokenExpired(Token token){
        if(token.equals(null) || LocalDateTime.now().isAfter(token.getExpiresAt())){
            return true;
        }
        return false;
    }
}
