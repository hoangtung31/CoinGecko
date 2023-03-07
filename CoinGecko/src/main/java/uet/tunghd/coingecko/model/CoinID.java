package uet.tunghd.coingecko.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class CoinID {
    @Id
    String id;
    String symbol;
    String name;
}
