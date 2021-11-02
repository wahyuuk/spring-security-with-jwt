package co.id.wahyu.auth.jwt.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class JwtService {

    @Value("{$token.access.expired}")
    private Integer ACCESS_TOKEN_EXPIRED;

    @Value("${token.refresh.expired}")
    private Integer REFRESH_TOKEN_EXPIRED;

    @Value("${token.secret.key}")
    private String SECRET_KEY;

    private final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET_KEY.getBytes());

    public String getUsernameFromToken(String token) {
        return decodedJWT(token).getSubject();
    }

    public List<String> getAuthoritiesFromToken(String token) {
        Claim claim = decodedJWT(token).getClaim("authorities");

        return claim.asList(String.class);
    }

    private DecodedJWT decodedJWT(String token) {
        try {
            JWTVerifier verifier = JWT.require(ALGORITHM)
                    .build();
            DecodedJWT decoded = verifier.verify(token);
            return decoded;
        } catch (JWTVerificationException exception) {
            throw exception;
        }
    }

    public Boolean isTokenNotExpired(String token) {
        if (decodedJWT(token).getExpiresAt().before(new Date())) {
            return false;
        }

        return true;
    }

    public String accessToken(String username, List<String> authorities, String urlRequest) {
        String token = JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis()+ ACCESS_TOKEN_EXPIRED * 60 * 1000))
                .withIssuer(urlRequest)
                .withClaim("authorities", authorities)
                .sign(ALGORITHM);

        return token;
    }

    public String refreshToken(String username, String urlRequest) {
        String token = JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis()+ REFRESH_TOKEN_EXPIRED * 60 * 1000))
                .withIssuer(urlRequest)
                .sign(ALGORITHM);

        return token;
    }
}
