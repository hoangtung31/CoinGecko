package uet.tunghd.coingecko.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uet.tunghd.coingecko.model.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

}
