package org.sopt.demo.auth.redis.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.sopt.demo.auth.UserAuthentication;
import org.sopt.demo.auth.redis.domain.Token;
import org.sopt.demo.auth.redis.repository.RedisTokenRepository;
import org.sopt.demo.common.jwt.JwtTokenProvider;
import org.sopt.demo.common.jwt.JwtValidationType;
import org.sopt.demo.service.dto.CreateTokenByRefreshTokenResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RedisTokenService {

    private final RedisTokenRepository redisTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, Object> redisTemplate;

    public CreateTokenByRefreshTokenResponse refreshToken(String token, HttpServletRequest request){
        JwtValidationType jwtValidationType = jwtTokenProvider.validateToken(token);

        if(jwtValidationType == JwtValidationType.VALID_JWT){
            Long memberId = jwtTokenProvider.getUserFromJwt(token);
            Token refreshToken = redisTokenRepository.findById(memberId).orElseThrow(
                    () -> new RuntimeException("refresh token expired or not exists")
            );
            UserAuthentication authentication = UserAuthentication.createUserAuthentication(memberId);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return CreateTokenByRefreshTokenResponse.of(jwtTokenProvider.issueAccessToken(authentication));
        }
        throw new RuntimeException("invalid token");
    }

    private Optional<Token> findById(Long memberId) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        Token token = (Token) valueOperations.get(memberId.toString());
        return Optional.ofNullable(token);
    }

    public <S extends Token> S save(S token) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(token.getId().toString(), token);
        return token;
    }

    public void deleteById(Long memberId) {
        redisTemplate.delete(memberId.toString());
    }
}
//    @Transactional
//    public void saveRefreshToken(
//            final Long memberId,
//            final String refreshToken
//    ){
//        Token token = Token.of(memberId, refreshToken);
//        redisTokenRepository.save(token);
//    }

//    @Transactional
//    public Long findIdByRefreshToken(
//            final String refreshToken
//    ){
//        return redisTokenRepository.findByRefreshToken(refreshToken)
//                .orElseThrow(() -> new RuntimeException("no token")).getId();
//    }
//
//    @Transactional
//    public void deleteRefreshToken(
//            final Long memberId
//    ){
//        Token token = redisTokenRepository.findById(memberId)
//                .orElseThrow(() -> new RuntimeException("no token"));
//        redisTokenRepository.delete(token);
//    }

