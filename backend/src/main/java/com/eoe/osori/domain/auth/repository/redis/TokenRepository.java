package com.eoe.osori.domain.auth.repository.redis;

import com.eoe.osori.domain.auth.domain.redis.Token;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends CrudRepository<Token, Long> {

    // refreshToken으로 RefreshToken을 찾아온다.
    Optional<Token> findByAccessToken(String accessToken);

    Optional<Token> findByRefreshToken(String refreshToken);

}