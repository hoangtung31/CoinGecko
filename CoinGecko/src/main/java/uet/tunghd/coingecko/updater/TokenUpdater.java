package uet.tunghd.coingecko.updater;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import uet.tunghd.coingecko.model.Token;
import uet.tunghd.coingecko.service.TokenService;

@Component
public class TokenUpdater {
    long startFrom = 137;
    Long count = Long.valueOf(startFrom);
    @Autowired
    @Lazy
    private TokenService tokenService;

    @Scheduled(cron = "0/6 * * * * ?")
    public void updateCoinContractAddress() {
        Token updatedToken = tokenService.updateToken(count);
        count += 1;
    }

    public void updateCount(HttpStatus status) {
        switch ( status) {
            case NOT_FOUND:
            case BAD_GATEWAY:
                count += 1;
                break;
            default:
                count += 1;
                break;
        }
    }
}
