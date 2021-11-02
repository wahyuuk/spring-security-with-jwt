package co.id.wahyu.auth.jwt.service;

import co.id.wahyu.auth.jwt.model.response.AuthResponse;
import co.id.wahyu.auth.jwt.util.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RefreshTokenService {

    public AuthResponse refreshToken(String token, String urlRequest) {
        JwtService jwtService = new JwtService();
        String username = jwtService.getUsernameFromToken(token);
        List<String> authorities = jwtService.getAuthoritiesFromToken(token)
                .stream().map(auth -> auth.getAuthority()).collect(Collectors.toList());

        if (token != null && jwtService.isTokenNotExpired(token)) {
            return new AuthResponse(jwtService.accessToken(username, authorities, urlRequest),
                    jwtService.refreshToken(username, urlRequest));
        }

        return null;
    }
}
