package uet.tunghd.coingecko.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import uet.tunghd.coingecko.model.Token;
import uet.tunghd.coingecko.service.TokenService;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RestController
public class RestSpringBootController {
    @Autowired
    TokenService tokenService;

    @RequestMapping("/hello")
    public String hello(){
        return "Hello world";
    }

    @GetMapping(value = "/callclienthello")
    private String getHelloClient() {
        String uri = "http://localhost:8085/hello";
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);
        return result;
    }

    // List all supported coins id
    @GetMapping(value = "/coins/list")
    private List<Token> coinList() {
        String uri = "https://api.coingecko.com/api/v3/coins/list";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<Token>> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Token>>() {
                });
        List<Token> tokenList = response.getBody();
//        tokenService.saveAll(tokenList);
        return tokenList;
    }

    @GetMapping(value = "/coins/update/{coinID}")
    private Token updateCoin(@PathVariable(name = "coinID") Long coinID) {
        Optional<Token> dbToken = tokenService.getCoinBy(coinID);
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
            Token lastestToken = response.getBody();
            return tokenService.updateTokenInfo(coinID, lastestToken);
        } else {
            return null;
        }
    }
}
