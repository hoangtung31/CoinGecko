package uet.tunghd.coingecko.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
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

    public Optional<Token> getTokenBy(Long id) {
        return tokenRepository.findById(id);
    }
    public List<Token> saveAll(List<Token> tokens) {
        return tokenRepository.saveAll(tokens);
    }

    public Token updateTokenInfo(Long id, Token token) {
        Token dbToken = tokenRepository.findById(id).orElse(null);
        if (dbToken != null) {
            dbToken.setContract_address(token.getContract_address());
            System.out.println(token);
            return tokenRepository.save(dbToken);
        } else {
            return null;
        }
    }

    public Token updateToken(Long tokenId) {
        Optional<Token> dbToken = this.getTokenBy(tokenId);
        System.out.println(dbToken);
        if(dbToken != null) {
            String uri = "https://api.coingecko.com/api/v3/coins/" + dbToken.get().getId();
            System.out.println(uri);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Token> response = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<Token>() {
                    }
            );
            Token latestToken = response.getBody();
            return this.updateTokenInfo(tokenId, latestToken);
        } else {
            return null;
        }
    }
}
