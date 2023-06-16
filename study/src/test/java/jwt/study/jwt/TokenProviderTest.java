package jwt.study.jwt;

import io.jsonwebtoken.Jwts;
import jwt.study.config.jwt.JwtProperties;
import jwt.study.config.jwt.TokenProvider;
import jwt.study.domain.User;
import jwt.study.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.util.Date;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class TokenProviderTest {

    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtProperties jwtProperties;

    @DisplayName("generateToken(): 유저 정보와 만료 기간을 전달해 토큰을 만들 수 있다.")
    @Test
    void generateToken() {
        //given
        User testUser = userRepository.save(User.builder()
                .email("user@email.com")
                .password("test")
                .build());

        //when
        String token = tokenProvider.generateToken(testUser, Duration.ofDays(14));

        //then
        Long userId = Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody()
                .get("id", Long.class);
        assertThat(userId).isEqualTo(testUser.getId());
    }

    @DisplayName("validToken(): 만료 된 토큰인 때에 유효성 검증에 실패한다.")
    @Test
    void validToken_invalidToken() {
        //given
        String token = JwtFactory.builder()
                .expiration(new Date(new Date().getTime() - Duration.ofDays(7).toMillis()))
                .build()
                .createToken(jwtProperties);

        //when
        boolean result = tokenProvider.validToken(token);
        //then
        assertThat(result).isFalse();
    }

    @DisplayName("validToken(): 유효한 토큰인 떄에 유효성 검증에 성공한다.")
    @Test
    void validToken_validToken() {
        //given
        String token = JwtFactory.withDefaultValues()
                .createToken(jwtProperties);
        //when
        boolean result = tokenProvider.validToken(token);
        //then
        assertThat(result).isFalse();
    }
}
