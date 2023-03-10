package uet.tunghd.coingecko.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import uet.tunghd.coingecko.model.Token;
import uet.tunghd.coingecko.repository.TokenRepository;
import uet.tunghd.coingecko.updater.TokenUpdater;

import java.util.List;
import java.util.Optional;

@Service
public class TokenService {
    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    @Lazy
    private TokenUpdater tokenUpdater;

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
            if (token.getContract_address() != null) {
                dbToken.setContract_address(token.getContract_address());
                System.out.println("[" + dbToken.get_id() + "] - " + "Update " + dbToken.getName() + "'s contract from: " + dbToken.getContract_address() + " to: " + token.getContract_address());
                return tokenRepository.save(dbToken);
            } else {
                System.out.println("[" + dbToken.get_id() + "] - " + "Update " + dbToken.getName() + "'s contract failure!");
                return dbToken;
            }
        } else {
            return null;
        }
    }

    public Token updateToken(Long tokenId) {
        Optional<Token> dbToken = this.getTokenBy(tokenId);
        if(dbToken != null) {
            String uri = "https://api.coingecko.com/api/v3/coins/" + dbToken.get().getId();
            RestTemplate restTemplate = new RestTemplate();
            try {
                ResponseEntity<Token> response = restTemplate.exchange(
                        uri,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<Token>() {
                        }
                );
                Token latestToken = response.getBody();
                return this.updateTokenInfo(tokenId, latestToken);
            } catch (HttpClientErrorException | HttpServerErrorException e) {
                tokenUpdater.updateCount(HttpStatus.valueOf(e.getStatusCode().value()) );
                return null;
            }

        } else {
            return null;
        }
    }
}
