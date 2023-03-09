package uet.tunghd.coingecko.updater;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import uet.tunghd.coingecko.model.Token;
import uet.tunghd.coingecko.service.TokenService;

@Component
public class TokenUpdater {
    Long count = Long.valueOf(46);

    @Autowired
    TokenService tokenService;

    @Scheduled(cron = "0/6 * * * * ?")
    public void updateCoinContractAddress() {
        Token updatedToken = tokenService.updateToken(count);
        if (updatedToken != null) {
            count += 1;
            System.out.println(updatedToken.getName() + " was updated!");
        }
        else {
            count = Long.valueOf(46);
            System.out.println("Reset successfully!");
        }
    }
}
