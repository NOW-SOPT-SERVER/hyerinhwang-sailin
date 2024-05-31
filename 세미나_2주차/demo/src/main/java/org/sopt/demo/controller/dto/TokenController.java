package org.sopt.demo.controller.dto;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.sopt.demo.auth.redis.service.RedisTokenService;
import org.sopt.demo.service.dto.CreateTokenByRefreshTokenResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/token")
public class TokenController {

    private final RedisTokenService redisTokenService;

    @PostMapping("/refresh")
    public ResponseEntity<CreateTokenByRefreshTokenResponse> refreshToken(HttpServletRequest request) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String token;

        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        token = authHeader.substring(7);
        CreateTokenByRefreshTokenResponse response = redisTokenService.refreshToken(token, request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Location", response.accessToken())
                .body(response);
    }
}
