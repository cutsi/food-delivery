package cut.food.fooddelivery.services;

import cut.food.fooddelivery.entities.Token;
import cut.food.fooddelivery.entities.User;
import cut.food.fooddelivery.repos.TokenRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

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
    public boolean isTokenExpired(Optional<Token> token){
        if(!token.isPresent()){
            return true;
        }
        if(LocalDateTime.now().isAfter(token.get().getExpiresAt())){
            return true;
        }
        return false;
    }

    private List<Token> getAllExpiredTokensFromUser(User user) {
        return tokenRepo.findAllByUser(user);
    }
    public void deleteAllExpiredTokensFromUser(User user){
        List<Token> userTokens = getAllExpiredTokensFromUser(user);
        for (Token token:userTokens) {
            tokenRepo.delete(token);
        }
    }
}
