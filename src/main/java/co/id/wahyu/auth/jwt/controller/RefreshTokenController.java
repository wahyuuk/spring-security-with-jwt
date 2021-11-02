package co.id.wahyu.auth.jwt.controller;

import co.id.wahyu.auth.jwt.model.response.AuthResponse;
import co.id.wahyu.auth.jwt.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/token")
public class RefreshTokenController {

    private final RefreshTokenService refreshTokenService;

    @Autowired
    public RefreshTokenController(RefreshTokenService refreshTokenService) {
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring("Bearer ".length());
        AuthResponse response = refreshTokenService.refreshToken(token, request.getRequestURL().toString());

        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        return new ResponseEntity<>("Refresh token has expired", HttpStatus.UNAUTHORIZED);
    }
}
