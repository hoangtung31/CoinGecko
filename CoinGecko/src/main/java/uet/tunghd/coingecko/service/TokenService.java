package uet.tunghd.coingecko.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uet.tunghd.coingecko.model.Token;
import uet.tunghd.coingecko.repository.TokenRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TokenService {
    @Autowired
    TokenRepository tokenRepository;

    public Token addCoinID(Token token) {
        return tokenRepository.save(token);
    }

    public Optional<Token> getCoinBy(Long id) {
        return tokenRepository.findById(id);
    }
    public List<Token> saveAll(List<Token> tokens) {
        return tokenRepository.saveAll(tokens);
    }

    public Token updateTokenInfo(Long id, Optional<Token> token) {
        Token dbToken = tokenRepository.findById(id).orElse(null);
        if (dbToken != null) {
            //dbToken.setDescription(token.get().getDescription());
            dbToken.setContract_address("token.get().getContract_address()");
            System.out.println(token);
            return tokenRepository.save(dbToken);
        } else {
            return null;
        }
    }
}
