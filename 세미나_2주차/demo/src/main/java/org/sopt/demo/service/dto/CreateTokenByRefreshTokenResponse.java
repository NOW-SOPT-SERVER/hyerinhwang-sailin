package org.sopt.demo.service.dto;

public record CreateTokenByRefreshTokenResponse(
        String accessToken
) {
    public static CreateTokenByRefreshTokenResponse of(
            String accessToken
    ){
        return new CreateTokenByRefreshTokenResponse(accessToken);
    }
}
