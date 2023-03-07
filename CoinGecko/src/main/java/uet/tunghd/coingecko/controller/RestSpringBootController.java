package uet.tunghd.coingecko.controller;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import uet.tunghd.coingecko.model.CoinID;

import java.lang.reflect.Type;
import java.util.List;

@RestController
public class RestSpringBootController {
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
    private List<CoinID> coinList() {
        String uri = "https://api.coingecko.com/api/v3/coins/list";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<CoinID>> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CoinID>>() {
                });
        List<CoinID> coinIDs = response.getBody();
        return coinIDs;
    }
}
